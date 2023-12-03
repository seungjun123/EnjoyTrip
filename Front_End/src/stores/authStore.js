import http from '@/common/axios.js'
import { useRouter } from 'vue-router'
import { reactive } from 'vue'
import { defineStore } from 'pinia'

import notLoginUserProfileImageUrl from '@/assets/img/noImage.png'
import LoginUserProfileImageUrl from '@/assets/img/ha2.jpg'
export const useAuthStore = defineStore('authStore', () => {
  const router = useRouter()
  // 로그인 여부, 사용자 이름, 프로필 이미지, 로그인 항목
  const authStore = reactive({
    isLogin: false,
    userSeq: '',
    userName: '',
    userProfileImageUrl: notLoginUserProfileImageUrl,
    userId: 'song22',
    userPassword: 'qqqqwwww!1'
  })

  const logout = async () => {
    console.log('logout')
    try {
      const { data } = await http.get('/users/logout')

      if (data.result == 'success') {
        setLogout()
      }
    } catch (error) {
      console.log(error)
    }
    // myPage에서 로그아웃 시 메인 페이지로 이동
    if (window.location.href == `http://192.168.1.5:8080/myPage?userSeq=${authStore.userSeq}`) {
      router.push('/')
    }
  }

  const setLogin = (payload) => {
    console.log('setLogin')
    sessionStorage.setItem('isLogin', 'true')
    sessionStorage.setItem('userSeq', payload.userSeq)
    sessionStorage.setItem('userName', payload.userName)
    sessionStorage.setItem('userPassword', payload.userPassword)
    sessionStorage.setItem(
      'userProfileImageUrl',
      payload.userProfileImageUrl == 'undefined'
        ? notLoginUserProfileImageUrl
        : LoginUserProfileImageUrl
    )
    console.log("jun : " + payload.LoginUserProfileImageUrl)

    console.log(payload)
    authStore.isLogin = payload.isLogin
    authStore.userSeq = payload.userSeq
    authStore.userName = payload.userName
    authStore.userProfileImageUrl =
      payload.userProfileImageUrl == 'undefined'
        ? notLoginUserProfileImageUrl
        : LoginUserProfileImageUrl
    console.log(authStore)
  }

  const setLogout = () => {
    console.log('setLogout')
    sessionStorage.removeItem('isLogin')
    sessionStorage.removeItem('userSeq')
    sessionStorage.removeItem('userName')
    sessionStorage.removeItem('userPassword')
    sessionStorage.removeItem('userProfileImageUrl')

    authStore.isLogin = false
    // authStore.userId = ''
    // authStore.userPassword = ''
    authStore.userName = ''
    authStore.userProfileImageUrl = ''
  }

  const updateUserInfo = (payload) => {
    console.log('updateUserInfo')
    sessionStorage.setItem('userEmail', payload.userEmail)
    sessionStorage.setItem('userPassword', payload.userPassword)
  }

  return { authStore, logout, setLogin, setLogout, updateUserInfo }
})
