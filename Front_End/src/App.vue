<script setup>
import { RouterView } from 'vue-router'
import { useAuthStore } from './stores/authStore'
import NavBar from './components/NavBar.vue'
import FooterPage from './components/FooterPage.vue'
const authStore = useAuthStore()

let isLogin = sessionStorage.getItem('isLogin')
// console.log(isLogin)
if (isLogin == 'true') {
  let userSeq = sessionStorage.getItem('userSeq')
  let userName = sessionStorage.getItem('userName')
  let userPassword = sessionStorage.getItem('userPassword')
  let userProfileImageUrl = sessionStorage.getItem('userProfileImageUrl')
  authStore.setLogin({
    isLogin: true,
    userSeq: userSeq,
    userName: userName,
    userPassword: userPassword,
    userProfileImageUrl: userProfileImageUrl
  })
}
</script>

<template>
  <NavBar></NavBar>

  <RouterView></RouterView>
  <FooterPage></FooterPage>
</template>

<style>
* {
  font-family: 'Do Hyeon';
  font-weight: 300;
}
</style>
