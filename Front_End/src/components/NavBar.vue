<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
    <div class="container">
      <!-- <a class="navbar-brand text-primary fw-bold" href="${root}/indexPage">
        <img src="./assets/img/ssafy_logo.png" alt="" width="60" /> Nice trip
      </a> -->
      <router-link to="/" class="navbar-brand text-primary"><img class="mx-1" :src="DoongHang" alt=""
          width="35" />동행</router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-lg-0">
          <li class="nav-item">
            <!-- <a class="nav-link" aria-current="page" href="${root}/triplistPage">관광지</a> -->
            <router-link to="/attraction" class="nav-link" aria-current="page">관광지</router-link>
          </li>
          <li class="nav-item">
            <!-- <a class="nav-link" aria-current="page" href="${root}/boardPage">공지사항</a> -->
            <router-link to="/board" class="nav-link" aria-current="page">소중한 후기</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/finddh" class="nav-link" aria-current="page">동행 찾기</router-link>
          </li>
        </ul>

        <!-- <c:if test="${empty userInfo}">
				<%-- session에 userInfo 객체 없는 경우(로그인 X) --%> -->
        <ul class="navbar-nav mb-2 me-2 mb-lg-0" v-show="!authStore.isLogin">
          <li class="nav-item" id="llogin" style="display: block" @click="showRegisterModal">
            <a class="nav-link" aria-current="page" href="#">회원가입</a>
          </li>
          <li class="nav-item" id="ggaip" style="display: block" @click="showLoginModal">
            <a class="nav-link" aria-current="page" href="#">로그인</a>
          </li>
          <!-- <li class="nav-item" id="ggaip" style="display: block" @click="showFindModal">
            <a class="nav-link" aria-current="page" href="#">비밀번호찾기</a>
          </li> -->
        </ul>
        <!-- </c:if> -->
        <!-- <%-- session에 userInfo 객체 있는 경우(로그인 O) --%>
				<c:if test="${empty userInfo}"> -->
        <ul class="navbar-nav mb-2 me-2 mb-lg-0" v-show="authStore.isLogin">
          <li class="nav-item">
            <router-link :to="{ name: 'myPage', query: { userSeq: authStore.userSeq } }" class="nav-link">
              <img :src="authStore.userProfileImageUrl" style="width: 24px; height: 24px; border-radius: 50%" />
              {{ authStore.userName }}님
            </router-link>
          </li>

          <li class="nav-item">
            <a class="nav-link" @click.prevent="logout">로그아웃</a>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="mmypage" role="button" data-bs-toggle="dropdown"
              aria-expanded="false">
              마이페이지
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li>
                <router-link :to="{ name: 'myPage', query: { userSeq: authStore.userSeq } }" class="dropdown-item">
                  My Page
                </router-link>
              </li>
              <li>
                <router-link to="favorite" class="dropdown-item">즐겨찾는 장소</router-link>
              </li>
              <li>
                <router-link to="mypost" class="dropdown-item" aria-current="page">나의 게시글</router-link>
              </li>
              <li>
                <router-link to="plan" class="dropdown-item">여행 계획</router-link>
              </li>
              <li>
                <router-link to="mypagechat" class="dropdown-item">동행 채팅</router-link>
              </li>
            </ul>
          </li>
        </ul>
        <!-- </c:if> -->
      </div>
    </div>
  </nav>
  <transition name="fade">
    <RegisterModal v-show="isRegisterModalVisible" @close="isRegisterModalVisible = false"
      @goLoginModal="showLoginModal" />
  </transition>
  <transition name="fade">
    <LoginModalVue v-show="isLoginModalVisible" @close="isLoginModalVisible = false" @goFindPassword="showFindModal"
      @goRegisterModal="showRegisterModal" />
  </transition>
  <transition name="fade">
    <FindModal v-show="isFindModalVisible" @close="isFindModalVisible = false" @goLoginModal="showLoginModal" />
  </transition>
  <transition name="fade">
    <UserInfoEditModalVue v-show="isUserInfoEditModalVisible" @close="isUserInfoEditModalVisible = false" />
  </transition>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import LoginModalVue from './modal/LoginModal.vue'
import RegisterModal from './modal/RegisterModal.vue'
import FindModal from './modal/FindModal.vue'
import DoongHang from '@/assets/img/동행.png'

const { authStore, logout } = useAuthStore()

// -- start : Modal 관리
const isRegisterModalVisible = ref(false)
const isLoginModalVisible = ref(false)
const isFindModalVisible = ref(false)
const isUserInfoEditModalVisible = ref(false)

function showRegisterModal(e) {
  isLoginModalVisible.value = false
  isRegisterModalVisible.value = true
}

function showLoginModal(e) {
  isFindModalVisible.value = false
  isRegisterModalVisible.value = false
  isLoginModalVisible.value = true
}

function showFindModal(e) {
  isLoginModalVisible.value = false
  isFindModalVisible.value = true
}

// -- end : Modal 관리
</script>

<style scoped>
.fade-enter-from {
  opacity: 0;
}

.fade-enter-active {
  transition: opacity 1s;
}

.fade-enter-to {
  opacity: 1;
}

* {
  font-size: 20px;
}
</style>
