<script setup>
import HeaderImg from '@/assets/img/동행메인.png'
</script>

<template>
  <div class="container-fluid bg-primary px-0 px-md-5 mb-5">
    <div class="row align-items-center px-3">
      <div class="col-lg-6 text-center text-lg-left">
        <h4 class="text-white mb-4 mt-5 mt-lg-0">삼성 청년 소프트웨어 아카데미</h4>
        <h1 class="display-3 font-weight-bold text-white">함께해요 DongHang</h1>
        <p class="text-white mb-4">여러분의 동행자를 찾아보세요. 즐거운 여행 되세요 .</p>
      </div>
      <div class="col-lg-5 text-center text-lg-right my-5">
        <img class="img-fluid mt-5" :src="HeaderImg" alt="" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.container-fluid {
  height: 94vh; /* 화면의 높이에 맞춥니다 */
  width: 100%; /* 너비를 100%로 설정합니다 */
  padding: 0; /* 상하좌우 패딩을 제거합니다 */
  margin: 0; /* 상하좌우 마진을 제거합니다 */
}
</style>
