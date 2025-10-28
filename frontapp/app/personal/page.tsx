"use client";

import React, { useState, useEffect } from 'react';
import { ChevronRight } from 'lucide-react';

// JWT 디코드 함수
const parseJWT = (token) => {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map((c) => {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
  } catch (error) {
    console.error('JWT 파싱 실패:', error);
    return null;
  }
};

export default function MyPage() {
  const [activeTab, setActiveTab] = useState('orders');
  const [activeSubTab, setActiveSubTab] = useState('product');
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [authTimeLeft, setAuthTimeLeft] = useState(0);
  const [editMode, setEditMode] = useState({});
  const [loading, setLoading] = useState(true);
  
  // API 엔드포인트 설정
  const API_BASE_URL = '/api'; // 여기만 수정하세요
  
  // JWT 토큰에서 userId 추출
  const getToken = () => localStorage.getItem('token') || sessionStorage.getItem('token');
  const token = getToken();
  const decoded = token ? parseJWT(token) : null;
  const userId = decoded?.userId || decoded?.id || decoded?.sub;
  
  // 사용자 정보
  const [userData, setUserData] = useState(null);
  const [tempData, setTempData] = useState(null);
  
  // 주문 내역
  const [orders, setOrders] = useState([]);
  
  // 배송지
  const [addresses, setAddresses] = useState([]);
  
  // 결제수단
  const [paymentMethods, setPaymentMethods] = useState([]);
  
  // 찜한 상품
  const [wishList, setWishList] = useState([]);
  
  // 팔로우 목록
  const [followList, setFollowList] = useState([]);
  
  // 쿠폰
  const [coupons, setCoupons] = useState([]);

  // 통계 정보
  const [stats, setStats] = useState({
    totalPoints: 0,
    totalCoupons: 0,
    totalReviews: 0,
    membershipLevel: 'Newbie'
  });

  // 초기 데이터 로드
  useEffect(() => {
    loadAllData();
  }, []);

  const loadAllData = async () => {
    setLoading(true);
    try {
      await Promise.all([
        fetchUserData(),
        fetchOrders(),
        fetchAddresses(),
        fetchPaymentMethods(),
        fetchWishList(),
        fetchFollowList(),
        fetchCoupons(),
        fetchStats()
      ]);
    } catch (error) {
      console.error('데이터 로드 실패:', error);
    } finally {
      setLoading(false);
    }
  };

  // 사용자 정보 조회
  const fetchUserData = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/users/${userId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      const data = await response.json();
      setUserData(data);
      setTempData(data);
    } catch (error) {
      console.error('사용자 정보 조회 실패:', error);
    }
  };

  // 주문 내역 조회
  const fetchOrders = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/orders?userId=${userId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      const data = await response.json();
      setOrders(data);
    } catch (error) {
      console.error('주문 내역 조회 실패:', error);
    }
  };

  // 배송지 조회
  const fetchAddresses = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/user-addresses?userId=${userId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      const data = await response.json();
      setAddresses(data);
    } catch (error) {
      console.error('배송지 조회 실패:', error);
    }
  };

  // 결제수단 조회
  const fetchPaymentMethods = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/payment-methods?userId=${userId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      const data = await response.json();
      setPaymentMethods(data);
    } catch (error) {
      console.error('결제수단 조회 실패:', error);
    }
  };

  // 찜한 상품 조회
  const fetchWishList = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/wishlists?userId=${userId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      const data = await response.json();
      setWishList(data);
    } catch (error) {
      console.error('찜 목록 조회 실패:', error);
    }
  };

  // 팔로우 목록 조회
  const fetchFollowList = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/follows?userId=${userId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      const data = await response.json();
      setFollowList(data);
    } catch (error) {
      console.error('팔로우 목록 조회 실패:', error);
    }
  };

  // 쿠폰 조회
  const fetchCoupons = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/coupons?userId=${userId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      const data = await response.json();
      setCoupons(data);
    } catch (error) {
      console.error('쿠폰 조회 실패:', error);
    }
  };

  // 통계 정보 조회
  const fetchStats = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/users/${userId}/stats`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      const data = await response.json();
      setStats(data);
    } catch (error) {
      console.error('통계 조회 실패:', error);
    }
  };

  // SMS 인증 타이머
  useEffect(() => {
    if (isAuthenticated && authTimeLeft > 0) {
      const timer = setInterval(() => {
        setAuthTimeLeft(prev => {
          if (prev <= 1) {
            setIsAuthenticated(false);
            return 0;
          }
          return prev - 1;
        });
      }, 1000);
      return () => clearInterval(timer);
    }
  }, [isAuthenticated, authTimeLeft]);

  // SMS 인증 요청
  const handleAuth = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/auth/sms`, {
        method: 'POST',
        headers: { 
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json' 
        },
        body: JSON.stringify({ userId, phone: userData?.mobilePhone })
      });
      
      if (response.ok) {
        setIsAuthenticated(true);
        setAuthTimeLeft(21600); // 6시간
        alert('SMS 인증이 완료되었습니다.');
      }
    } catch (error) {
      console.error('SMS 인증 실패:', error);
      alert('인증에 실패했습니다.');
    }
  };

  const formatTime = (seconds) => {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    return `${hours}시간 ${minutes}분`;
  };

  // 수정 모드 시작
  const handleEdit = (section) => {
    if (!isAuthenticated) {
      alert('정보 수정을 위해서는 SMS 인증이 필요합니다.');
      return;
    }
    setEditMode({ ...editMode, [section]: true });
    setTempData({...userData});
  };

  // 사용자 정보 저장
  const handleSave = async (section) => {
    try {
      const response = await fetch(`${API_BASE_URL}/users/${userId}`, {
        method: 'PUT',
        headers: { 
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json' 
        },
        body: JSON.stringify(tempData)
      });
      
      if (response.ok) {
        const updatedData = await response.json();
        setUserData(updatedData);
        setEditMode({ ...editMode, [section]: false });
        alert('정보가 수정되었습니다.');
      }
    } catch (error) {
      console.error('정보 수정 실패:', error);
      alert('수정에 실패했습니다.');
    }
  };

  // 수정 취소
  const handleCancel = (section) => {
    setTempData({...userData});
    setEditMode({ ...editMode, [section]: false });
  };

  // 배송지 추가
  const handleAddAddress = async (addressData) => {
    try {
      const response = await fetch(`${API_BASE_URL}/user-addresses`, {
        method: 'POST',
        headers: { 
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json' 
        },
        body: JSON.stringify({ ...addressData, userId })
      });
      
      if (response.ok) {
        await fetchAddresses();
        alert('배송지가 추가되었습니다.');
      }
    } catch (error) {
      console.error('배송지 추가 실패:', error);
      alert('배송지 추가에 실패했습니다.');
    }
  };

  // 배송지 수정
  const handleUpdateAddress = async (addressId, addressData) => {
    try {
      const response = await fetch(`${API_BASE_URL}/user-addresses/${addressId}`, {
        method: 'PUT',
        headers: { 
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json' 
        },
        body: JSON.stringify(addressData)
      });
      
      if (response.ok) {
        await fetchAddresses();
        alert('배송지가 수정되었습니다.');
      }
    } catch (error) {
      console.error('배송지 수정 실패:', error);
      alert('배송지 수정에 실패했습니다.');
    }
  };

  // 배송지 삭제
  const handleDeleteAddress = async (addressId) => {
    if (!confirm('정말 삭제하시겠습니까?')) return;
    
    try {
      const response = await fetch(`${API_BASE_URL}/user-addresses/${addressId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      
      if (response.ok) {
        await fetchAddresses();
        alert('배송지가 삭제되었습니다.');
      }
    } catch (error) {
      console.error('배송지 삭제 실패:', error);
      alert('배송지 삭제에 실패했습니다.');
    }
  };

  // 결제수단 추가
  const handleAddPayment = async (paymentData) => {
    try {
      const response = await fetch(`${API_BASE_URL}/payment-methods`, {
        method: 'POST',
        headers: { 
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json' 
        },
        body: JSON.stringify({ ...paymentData, userId })
      });
      
      if (response.ok) {
        await fetchPaymentMethods();
        alert('결제수단이 추가되었습니다.');
      }
    } catch (error) {
      console.error('결제수단 추가 실패:', error);
      alert('결제수단 추가에 실패했습니다.');
    }
  };

  // 찜 삭제
  const handleRemoveWish = async (wishlistId) => {
    try {
      const response = await fetch(`${API_BASE_URL}/wishlists/${wishlistId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      
      if (response.ok) {
        await fetchWishList();
      }
    } catch (error) {
      console.error('찜 삭제 실패:', error);
    }
  };

  // 팔로우 취소
  const handleUnfollow = async (followId) => {
    try {
      const response = await fetch(`${API_BASE_URL}/follows/${followId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      
      if (response.ok) {
        await fetchFollowList();
      }
    } catch (error) {
      console.error('언팔로우 실패:', error);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-lg">로딩중...</div>
      </div>
    );
  }

  if (!token || !userId) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-center">
          <p className="text-lg mb-4">로그인이 필요합니다.</p>
          <button 
            onClick={() => window.location.href = '/auth/login'}
            className="px-6 py-3 bg-black text-white hover:bg-gray-800"
          >
            로그인하기
          </button>
        </div>
      </div>
    );
  }

  if (!userData) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-lg">사용자 정보를 불러올 수 없습니다.</div>
      </div>
    );
  }

  return (
    <div className="flex min-h-screen bg-white">
      {/* 왼쪽 사이드바 */}
      <div className="w-64 border-r border-gray-200 p-8">
        <h1 className="text-2xl font-bold mb-8">{userData.userName}</h1>
        
        <nav className="space-y-6">
          <div>
            <h2 className="text-xs font-semibold text-gray-500 mb-3">나의 쇼핑정보</h2>
            <ul className="space-y-2 text-sm">
              <li>
                <button 
                  onClick={() => setActiveTab('orders')}
                  className={`${activeTab === 'orders' ? 'font-semibold' : 'text-gray-600'}`}
                >
                  주문배송조회
                </button>
              </li>
              <li>
                <button 
                  onClick={() => setActiveTab('reviews')}
                  className={`${activeTab === 'reviews' ? 'font-semibold' : 'text-gray-600'}`}
                >
                  상품 리뷰
                </button>
              </li>
              <li>
                <button 
                  onClick={() => setActiveTab('coupons')}
                  className={`${activeTab === 'coupons' ? 'font-semibold' : 'text-gray-600'}`}
                >
                  쿠폰
                </button>
              </li>
            </ul>
          </div>

          <div>
            <h2 className="text-xs font-semibold text-gray-500 mb-3">나의 계정정보</h2>
            <ul className="space-y-2 text-sm">
              <li>
                <button 
                  onClick={() => setActiveTab('profile')}
                  className={`${activeTab === 'profile' ? 'font-semibold' : 'text-gray-600'}`}
                >
                  회원정보수정
                </button>
              </li>
              <li>
                <button 
                  onClick={() => setActiveTab('addresses')}
                  className={`${activeTab === 'addresses' ? 'font-semibold' : 'text-gray-600'}`}
                >
                  배송지 관리
                </button>
              </li>
              <li>
                <button 
                  onClick={() => setActiveTab('payment')}
                  className={`${activeTab === 'payment' ? 'font-semibold' : 'text-gray-600'}`}
                >
                  결제수단
                </button>
              </li>
              <li>
                <button 
                  onClick={() => setActiveTab('wishlist')}
                  className={`${activeTab === 'wishlist' ? 'font-semibold' : 'text-gray-600'}`}
                >
                  나의 좋아요
                </button>
              </li>
            </ul>
          </div>

          <div>
            <h2 className="text-xs font-semibold text-gray-500 mb-3">고객센터</h2>
            <ul className="space-y-2 text-sm text-gray-600">
              <li>1:1 문의</li>
              <li>상품 Q&A 내역</li>
              <li>FAQ</li>
            </ul>
          </div>
        </nav>
      </div>

      {/* 오른쪽 콘텐츠 */}
      <div className="flex-1">
        <div className="max-w-5xl mx-auto p-8">
          
          {/* SMS 인증 배너 */}
          {!isAuthenticated && (
            <div className="mb-6 p-4 bg-gray-50 border border-gray-200 flex items-center justify-between text-sm">
              <span>정보 수정을 위해 SMS 인증이 필요합니다 (6시간 유효)</span>
              <button
                onClick={handleAuth}
                className="px-4 py-2 bg-black text-white text-xs hover:bg-gray-800"
              >
                인증하기
              </button>
            </div>
          )}

          {isAuthenticated && (
            <div className="mb-6 p-4 bg-green-50 border border-green-200 text-sm">
              인증 완료 - 남은 시간: {formatTime(authTimeLeft)}
            </div>
          )}

          {/* 등급 및 포인트 정보 */}
          <div className="mb-8 bg-gray-900 text-white">
            <table className="w-full">
              <thead>
                <tr className="border-b border-gray-700">
                  <th className="py-3 px-4 text-left text-xs font-normal text-gray-400">멤버십 등급</th>
                  <th className="py-3 px-4 text-left text-xs font-normal text-gray-400">적립금</th>
                  <th className="py-3 px-4 text-left text-xs font-normal text-gray-400">상품 리뷰</th>
                  <th className="py-3 px-4 text-left text-xs font-normal text-gray-400">쿠폰</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td className="py-4 px-4 text-2xl font-bold">{stats.membershipLevel}</td>
                  <td className="py-4 px-4 text-2xl font-bold">{stats.totalPoints}</td>
                  <td className="py-4 px-4 text-2xl font-bold">{stats.totalReviews}</td>
                  <td className="py-4 px-4 text-2xl font-bold">{stats.totalCoupons}</td>
                </tr>
              </tbody>
            </table>
          </div>

          {/* 주문배송조회 */}
          {activeTab === 'orders' && (
            <div>
              <div className="flex items-center justify-between mb-6 pb-4 border-b border-gray-900">
                <h2 className="text-xl font-bold">최근 주문</h2>
                <button className="text-sm flex items-center gap-1">
                  더보기 <ChevronRight size={16} />
                </button>
              </div>

              {orders.length === 0 ? (
                <div className="text-center py-20 text-gray-500">
                  주문 내역이 없습니다.
                </div>
              ) : (
                <div className="space-y-4">
                  {orders.map(order => (
                    <div key={order.orderId} className="border border-gray-200 p-6">
                      <div className="flex justify-between items-start mb-4">
                        <div>
                          <p className="text-xs text-gray-500 mb-1">{order.createdDate}</p>
                          <p className="text-xs text-gray-500">주문번호: {order.orderCord}</p>
                        </div>
                        <span className="text-xs px-3 py-1 bg-gray-100">
                          {order.deliveryStatus}
                        </span>
                      </div>
                      
                      {order.items && order.items.map((item, idx) => (
                        <div key={idx} className="mb-2">
                          <p className="font-medium">{item.productName}</p>
                          <p className="text-sm text-gray-600">
                            {item.price?.toLocaleString()}원 / {item.quantity}개
                          </p>
                        </div>
                      ))}

                      <div className="mt-4 pt-4 border-t border-gray-100 flex justify-between items-center">
                        <p className="text-xs text-gray-500">
                          {order.trackingNumber && `운송장: ${order.trackingNumber}`}
                        </p>
                        <p className="font-bold">
                          총 {order.totalPrice?.toLocaleString()}원
                        </p>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}

          {/* 회원정보수정 */}
          {activeTab === 'profile' && (
            <div>
              <div className="flex items-center justify-between mb-6 pb-4 border-b border-gray-900">
                <h2 className="text-xl font-bold">회원정보수정</h2>
                {!editMode.profile ? (
                  <button
                    onClick={() => handleEdit('profile')}
                    className="px-4 py-2 bg-black text-white text-sm hover:bg-gray-800"
                  >
                    수정
                  </button>
                ) : (
                  <div className="flex gap-2">
                    <button
                      onClick={() => handleSave('profile')}
                      className="px-4 py-2 bg-black text-white text-sm hover:bg-gray-800"
                    >
                      저장
                    </button>
                    <button
                      onClick={() => handleCancel('profile')}
                      className="px-4 py-2 border border-gray-300 text-sm hover:bg-gray-50"
                    >
                      취소
                    </button>
                  </div>
                )}
              </div>

              <div className="space-y-6 max-w-2xl">
                <div className="flex">
                  <label className="w-32 text-sm font-medium pt-2">이름</label>
                  {editMode.profile ? (
                    <input
                      type="text"
                      value={tempData.userName}
                      onChange={(e) => setTempData({...tempData, userName: e.target.value})}
                      className="flex-1 px-3 py-2 border border-gray-300 focus:outline-none focus:border-black"
                    />
                  ) : (
                    <p className="flex-1 pt-2">{userData.userName}</p>
                  )}
                </div>

                <div className="flex">
                  <label className="w-32 text-sm font-medium pt-2">닉네임</label>
                  {editMode.profile ? (
                    <input
                      type="text"
                      value={tempData.nickName}
                      onChange={(e) => setTempData({...tempData, nickName: e.target.value})}
                      className="flex-1 px-3 py-2 border border-gray-300 focus:outline-none focus:border-black"
                    />
                  ) : (
                    <p className="flex-1 pt-2">{userData.nickName}</p>
                  )}
                </div>

                <div className="flex">
                  <label className="w-32 text-sm font-medium pt-2">이메일</label>
                  {editMode.profile ? (
                    <input
                      type="email"
                      value={tempData.email}
                      onChange={(e) => setTempData({...tempData, email: e.target.value})}
                      className="flex-1 px-3 py-2 border border-gray-300 focus:outline-none focus:border-black"
                    />
                  ) : (
                    <p className="flex-1 pt-2">{userData.email}</p>
                  )}
                </div>

                <div className="flex">
                  <label className="w-32 text-sm font-medium pt-2">휴대폰</label>
                  {editMode.profile ? (
                    <input
                      type="tel"
                      value={tempData.mobilePhone}
                      onChange={(e) => setTempData({...tempData, mobilePhone: e.target.value})}
                      className="flex-1 px-3 py-2 border border-gray-300 focus:outline-none focus:border-black"
                    />
                  ) : (
                    <p className="flex-1 pt-2">{userData.mobilePhone}</p>
                  )}
                </div>

                <div className="flex">
                  <label className="w-32 text-sm font-medium pt-2">생년월일</label>
                  {editMode.profile ? (
                    <input
                      type="date"
                      value={tempData.birth}
                      onChange={(e) => setTempData({...tempData, birth: e.target.value})}
                      className="flex-1 px-3 py-2 border border-gray-300 focus:outline-none focus:border-black"
                    />
                  ) : (
                    <p className="flex-1 pt-2">{userData.birth}</p>
                  )}
                </div>

                <div className="flex">
                  <label className="w-32 text-sm font-medium pt-2">성별</label>
                  {editMode.profile ? (
                    <select
                      value={tempData.gender}
                      onChange={(e) => setTempData({...tempData, gender: e.target.value})}
                      className="flex-1 px-3 py-2 border border-gray-300 focus:outline-none focus:border-black"
                    >
                      <option value="MALE">남성</option>
                      <option value="FEMALE">여성</option>
                    </select>
                  ) : (
                    <p className="flex-1 pt-2">{userData.gender === 'MALE' ? '남성' : '여성'}</p>
                  )}
                </div>
              </div>
            </div>
          )}

          {/* 배송지 관리 */}
          {activeTab === 'addresses' && (
            <div>
              <div className="flex items-center justify-between mb-6 pb-4 border-b border-gray-900">
                <h2 className="text-xl font-bold">배송지 관리</h2>
                <button 
                  onClick={() => alert('배송지 추가 모달을 열어주세요')}
                  className="px-4 py-2 bg-black text-white text-sm hover:bg-gray-800"
                >
                  + 새 배송지 추가
                </button>
              </div>

              <div className="space-y-4">
                {addresses.map(addr => (
                  <div key={addr.userAddressId} className="border border-gray-200 p-6">
                    <div className="flex justify-between items-start mb-3">
                      <div className="flex items-center gap-2">
                        <span className="font-medium">{addr.recipientName}</span>
                        {addr.isDefault && (
                          <span className="text-xs px-2 py-1 bg-black text-white">기본배송지</span>
                        )}
                      </div>
                      <div className="flex gap-2">
                        <button 
                          onClick={() => alert('배송지 수정 모달을 열어주세요')}
                          className="text-xs underline"
                        >
                          수정
                        </button>
                        <button 
                          onClick={() => handleDeleteAddress(addr.userAddressId)}
                          className="text-xs underline text-red-600"
                        >
                          삭제
                        </button>
                      </div>
                    </div>
                    <p className="text-sm text-gray-600 mb-1">[{addr.zipcode}]</p>
                    <p className="text-sm">{addr.baseAddress}</p>
                    <p className="text-sm text-gray-600">{addr.detailAddress}</p>
                  </div>
                ))}
              </div>
            </div>
          )}

          {/* 결제수단 */}
          {activeTab === 'payment' && (
            <div>
              <div className="flex items-center justify-between mb-6 pb-4 border-b border-gray-900">
                <h2 className="text-xl font-bold">결제수단</h2>
                <button 
                  onClick={() => alert('결제수단 추가 모달을 열어주세요')}
                  className="px-4 py-2 bg-black text-white text-sm hover:bg-gray-800"
                >
                  + 결제수단 추가
                </button>
              </div>

              <div className="space-y-4">
                {paymentMethods.map(method => (
                  <div key={method.paymentId} className="border border-gray-200 p-6">
                    <div className="flex justify-between items-start">
                      <div>
                        <div className="flex items-center gap-2 mb-2">
                          <span className="font-medium">
                            {method.type === 'CARD' ? '신용카드' : '계좌이체'}
                          </span>
                          {method.defaultPayment && (
                            <span className="text-xs px-2 py-1 bg-black text-white">기본결제</span>
                          )}
                        </div>
                        <p className="text-sm">{method.cardCompany || method.bankName}</p>
                        <p className="text-sm text-gray-600">{method.cardNumber || method.accountNumber}</p>
                      </div>
                      <button className="text-xs underline">수정</button>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}

          {/* 나의 좋아요 */}
          {activeTab === 'wishlist' && (
            <div>
              <div className="mb-6 pb-4 border-b border-gray-900">
                <h2 className="text-xl font-bold mb-4">나의 좋아요</h2>
                <div className="flex gap-8 text-sm">
                  <button
                    onClick={() => setActiveSubTab('product')}
                    className={`pb-2 ${activeSubTab === 'product' ? 'border-b-2 border-black font-semibold' : 'text-gray-500'}`}
                  >
                    Product
                  </button>
                  <button
                    onClick={() => setActiveSubTab('brand')}
                    className={`pb-2 ${activeSubTab === 'brand' ? 'border-b-2 border-black font-semibold' : 'text-gray-500'}`}
                  >
                    Brand
                  </button>
                </div>
              </div>

              {activeSubTab === 'product' && (
                <div>
                  {wishList.length === 0 ? (
                    <div className="text-center py-20 text-gray-500">
                      좋아요한 상품이 없습니다.
                    </div>
                  ) : (
                    <div className="grid grid-cols-3 gap-6">
                      {wishList.map(item => (
                        <div key={item.wishlistId} className="border border-gray-200">
                          <div className="aspect-square bg-gray-100"></div>
                          <div className="p-4">
                            <p className="text-sm mb-1">{item.productName}</p>
                            <p className="font-semibold">{item.price?.toLocaleString()}원</p>
                            <button 
                              onClick={() => handleRemoveWish(item.wishlistId)}
                              className="text-xs text-gray-500 hover:text-red-600 mt-2"
                            >
                              삭제
                            </button>
                          </div>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              )}

              {activeSubTab === 'brand' && (
                <div>
                  {followList.length === 0 ? (
                    <div className="text-center py-20 text-gray-500">
                      팔로우한 브랜드가 없습니다.
                    </div>
                  ) : (
                    <div className="space-y-4">
                      {followList.map(follow => (
                        <div key={follow.followId} className="flex items-center justify-between border border-gray-200 p-6">
                          <div className="flex items-center gap-4">
                            <div className="w-16 h-16 bg-gray-100"></div>
                            <div>
                              <p className="font-medium">{follow.studioName}</p>
                              <p className="text-xs text-gray-500">{follow.createdAt}</p>
                            </div>
                          </div>
                          <button 
                            onClick={() => handleUnfollow(follow.followId)}
                            className="px-4 py-2 border border-gray-300 text-sm hover:bg-gray-50"
                          >
                            언팔로우
                          </button>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              )}
            </div>
          )}

          {/* 쿠폰 */}
          {activeTab === 'coupons' && (
            <div>
              <div className="mb-6 pb-4 border-b border-gray-900">
                <h2 className="text-xl font-bold">쿠폰</h2>
              </div>

              {coupons.length === 0 ? (
                <div className="text-center py-20 text-gray-500">
                  보유한 쿠폰이 없습니다.
                </div>
              ) : (
                <div className="space-y-4">
                  {coupons.map(coupon => (
                    <div key={coupon.couponId} className="border-2 border-dashed border-gray-300 p-6">
                      <div className="flex justify-between items-start">
                        <div>
                          <p className="font-bold text-lg mb-2">{coupon.name}</p>
                          <p className="text-sm text-gray-600 mb-1">
                            {coupon.discountCategory === 'RATE' 
                              ? `${coupon.discountRate}% 할인` 
                              : `${coupon.discountAmount?.toLocaleString()}원 할인`}
                          </p>
                          <p className="text-xs text-gray-500">유효기간: {coupon.expiredAt}</p>
                        </div>
                        <span className={`text-xs px-3 py-1 ${
                          coupon.couponStatus 
                            ? 'bg-black text-white' 
                            : 'bg-gray-200 text-gray-600'
                        }`}>
                          {coupon.couponStatus ? '사용가능' : '사용완료'}
                        </span>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}

          {/* 상품리뷰 */}
          {activeTab === 'reviews' && (
            <div>
              <div className="mb-6 pb-4 border-b border-gray-900">
                <h2 className="text-xl font-bold">상품 리뷰</h2>
              </div>
              <div className="text-center py-20 text-gray-500">
                작성한 리뷰가 없습니다.
              </div>
            </div>
          )}

        </div>
      </div>
    </div>
  );
}