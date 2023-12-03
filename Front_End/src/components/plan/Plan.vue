<script setup>
import http from '@/common/axios.js'
import { ref, computed, onMounted } from 'vue'
import { useAuthStore } from '../../stores/authStore'
import { usePlanStore } from '@/stores/planStore'
import { useAttractionStore } from '@/stores/attractionStore'
import PlanRegisterModal from './modal/PlanRegister.vue'
import PlanUpdateModal from './modal/PlanUpdate.vue'
import notAttractionImageUrl from '/src/assets/img/noImage.png'

const { authStore } = useAuthStore()
const { planStore, planDetailStore, plan, planDetail, deletePlan, planListRegist, planListDelete } =
  usePlanStore()
const { attractionStore, attractionListLike } = useAttractionStore()

const isPlanDetailShown = ref(false)

onMounted(() => {
  plan({
    userSeq: authStore.userSeq
  })
  planDetail(sessionStorage.getItem('planId'))
  attractionListLike(50)
  kakaoMapInit()
  favoriteList()
})

// Modal Logic
const isPlanRegisterModalVisible = ref(false)
const isPlanUpdateModalVisible = ref(false)

const showPlanRegisterModal = () => {
  console.log('showPlanRegisterModal')
  isPlanRegisterModalVisible.value = true
}

const showPlanUpdateModal = (planId) => {
  console.log('showPlanUpdateModal')
  isPlanUpdateModalVisible.value = true
  planDetailStore.planId = planId
  planDetail(planId)
  console.log(planDetailStore)
}

const refreshPage = async () => {
  plan({
    userSeq: authStore.userSeq
  })
}
// Modal Logic

// Sidebar Logic
const isSidebarPlanOpen = ref(true)
const isSidebarPlanSearchOpen = ref(false)
const isSidebarPlanDetailOpen = ref(false)
const days = ref()

const showSidebarPlan = () => {
  isSidebarPlanOpen.value = true
  isSidebarPlanSearchOpen.value = false
  isSidebarPlanDetailOpen.value = false
  isPlanDetailShown.value = false
  kakaoMapInit()
}

const showSidebarPlanDetail = () => {
  isSidebarPlanOpen.value = false
  isSidebarPlanSearchOpen.value = true
  isSidebarPlanDetailOpen.value = true
}

const showPlanDetail = async (id) => {
  console.log('showPlanDetail')
  sessionStorage.setItem('planId', id)
  await planDetail(sessionStorage.getItem('planId'))

  const endDate = planDetailStore.endDate.split('-')
  const startDate = planDetailStore.startDate.split('-')
  days.value =
    Number(endDate[1]) * 30 +
    Number(endDate[2]) -
    (Number(startDate[1]) * 30 + Number(startDate[2])) +
    1

  isPlanDetailShown.value = true
  showSidebarPlanDetail()
}
// Sidebar Logic

// Attraction Logic
const keyword = ref()

const attractionSearch = (e) => {
  attractionStore.searchWord = keyword.value
  attractionListLike(50)
  attrTab.value = true
}

const sendCardDetail = (attractionDetail) => {
  console.log(attractionDetail)
  kakaoMap(attractionDetail.addr1)
}
// Attraction Logic

// Kakao Map Logic
let mapContainer = ref('')
let mapOption = ref('')
let map = ref('')

const kakaoMapInit = () => {
  let mapRef = null

  mapContainer.value = document.querySelector('#map') // 지도를 표시할 div
  console.log('찍혀 ??')
  console.log(mapContainer)

  mapOption.value = {
    center: new kakao.maps.LatLng(35.0959015, 128.8562159), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
  }

  // 지도를 생성합니다
  map.value = new kakao.maps.Map(mapContainer.value, mapOption.value)
}

const kakaoMap = (addr) => {
  // 장소 검색 객체를 생성합니다
  const ps = new kakao.maps.services.Places()

  // 키워드로 장소를 검색합니다
  ps.keywordSearch(addr, placesSearchCB)

  // 검색 완료 시 호출되는 콜백함수
  function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
      var bounds = new kakao.maps.LatLngBounds()

      displayMarker(data[0])
      bounds.extend(new kakao.maps.LatLng(data[0].y, parseFloat(data[0].x) + 0.001))

      map.value.setBounds(bounds)
    }
  }

  // 마커를 표시하는 함수입니다
  function displayMarker(place) {
    var marker = new kakao.maps.Marker({
      map: map.value,
      position: new kakao.maps.LatLng(place.y, place.x)
    })

    kakao.maps.event.addListener(marker, 'click', function () {
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 })
      infowindow.setContent(
        '<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>'
      )
      infowindow.open(map.value, marker)
    })
  }
}
// Kakao Map Logic

const uniqueDays = computed(() => {
  const uniqueDaysSet = new Set(planDetailStore.list.map((plan) => plan.day))
  const maxDay = Math.max(...uniqueDaysSet) // Find the maximum day

  // Generate an array starting from 1 to the maximum day
  return Array.from({ length: maxDay }, (_, index) => index + 1)
})

// Plan Logic
const confirmDelete = (planId, planTitle, index) => {
  const isConfirmed = window.confirm(`정말로 ${planTitle} 여행 계획을 삭제하시겠습니까?`)

  if (isConfirmed) {
    deletePlan(planId, planTitle, index)
  }
}

const registPlanList = (contentId, day) => {
  console.log('registPlanList')
  let planObj = {
    planId: sessionStorage.getItem('planId'),
    contentId: contentId,
    day
  }
  planListRegist(planObj)
}

const deletePlanList = (id) => {
  console.log('deletePlanList')
  planListDelete(id)
}
// Plan Logic

// Favorite Logic
const attrTab = ref(true)
const favorites = ref()

const favoriteList = async () => {
  try {
    let { data } = await http.get('/users/favorite') // {params:params} 의 shorthand property
    favorites.value = data.attrlist
  } catch (error) {
    console.log(error)
  }
}

const showAttrTab = () => {
  attrTab.value = true
}

const showFavorTab = () => {
  attrTab.value = false
}
// Favorite Logic
</script>

<template>
  <!-- Sidebar -->
  <div class="sidebar">
    <div>
      <!-- Add your sidebar content here -->
      <ul class="nav flex-column mt-2">
        <li class="nav-item mx-2">
          <a
            @click="showSidebarPlan"
            class="nav-link mb-2"
            :class="{
              'nav-link mb-2 active': isSidebarPlanOpen,
              'nav-link mb-2': !isSidebarPlanOpen
            }"
          >
            <!-- Add your icons or images here -->
            여행 계획
          </a>
          <a
            @click="showSidebarPlanDetail"
            class="nav-link mb-2"
            :class="{
              'nav-link mb-2 active': !isSidebarPlanOpen,
              'nav-link mb-2': isSidebarPlanOpen
            }"
          >
            <!-- Add your icons or images here -->
            장소 선택
          </a>
        </li>
        <!-- More list items -->
      </ul>
    </div>
  </div>

  <div class="sidebar-plan" v-show="isSidebarPlanOpen">
    <div class="container mt-3">
      <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">여행 계획 리스트</div>
      <div class="row justify-content-center mt-4 mb-5" style="width: 100%">
        <div class="col-12 col-sm-12 col-lg-12 col-xl-12 text-center mb-3">
          <!-- 수정된 부분 -->
          <button @click="showPlanRegisterModal" class="btn btn-primary">
            새로운 계획 생성하기
          </button>
        </div>
        <div
          class="col-12 col-sm-8 col-lg-8 col-xl-10 mt-3 mb-4"
          v-for="(plan, index) in planStore.list"
          :key="index"
        >
          <!-- Card - start -->
          <div class="card h-100">
            <div class="card-body d-flex flex-column" @click.prevent="showPlanDetail(plan.planId)">
              <h5 class="card-title">{{ plan.planTitle }}</h5>
              <p class="card-text">{{ plan.content }}</p>
              <div class="mt-auto">
                <div class="d-flex justify-content-between">
                  <span>예산 : {{ plan.budget }}만원</span>
                  <span class="fw-bold">{{ plan.startDate }} ~ {{ plan.endDate }}</span>
                </div>
              </div>
            </div>
            <div class="d-flex justify-content-between mt-2">
              <button @click="showPlanUpdateModal(plan.planId)" class="btn btn-warning">
                수정
              </button>
              <button
                @click="confirmDelete(plan.planId, plan.planTitle, index)"
                class="btn btn-danger"
              >
                삭제
              </button>
            </div>
          </div>
          <!-- Card - end -->
        </div>
      </div>
    </div>
  </div>

  <div class="sidebar-plan-search" v-show="isSidebarPlanSearchOpen">
    <!-- Main content -->
    <div class="container mt-3 mb-5">
      <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">여행지</div>
      <!-- 관광지 검색 start -->
      <form class="d-flex my-3 justify-content-center" role="search" onsubmit="prevSubmit();">
        <input
          id="search-keyword"
          class="form-control me-3"
          type="search"
          placeholder="검색어"
          aria-label="검색어"
          name="keyword"
          v-model="keyword"
        />
        <button
          id="btn-search"
          class="btn btn-outline-success col-sm-3"
          type="submit"
          @click.prevent="attractionSearch"
        >
          검색
        </button>
      </form>
      <form class="d-flex my-3 justify-content-center mx-2">
        <button
          @click.prevent="showAttrTab"
          class="form-control me-3"
          :class="{ 'text-info': attrTab }"
        >
          여행지 검색
        </button>
        <button
          @click.prevent="showFavorTab"
          class="form-control me-3"
          :class="{ 'text-info': !attrTab }"
        >
          즐겨찾기
        </button>
      </form>
      <!-- 관광지 검색 end -->
      <div v-show="attrTab">
        <div class="row" style="width: 100%">
          <div
            class="col-12 col-sm-12 col-lg-12 col-xl-12 mb-4"
            v-for="(attraction, index) in attractionStore.list"
            :key="attraction.contentId"
          >
            <!-- Card - start -->
            <div
              class="card h-100 button-hover"
              @click="sendCardDetail(attraction)"
              style="width: 325px"
            >
              <div class="card-body d-flex flex-row align-items-center">
                <!-- Move the image outside the card-body -->
                <div style="height: 100px; width: 100px">
                  <img
                    :src="attraction.firstImage || notAttractionImageUrl"
                    class="card-img-top"
                    alt="..."
                    style="height: 100px; width: 100px; object-fit: cover"
                  />
                </div>

                <!-- Other card elements -->
                <div class="ml-3 flex-grow-1 mx-2">
                  <p class="card-title fs-6">{{ attraction.title }}</p>
                  <!-- Additional content -->
                  <p class="card-content">{{ attraction.addr1 }}</p>
                  <div class="d-flex justify-content-between align-items-center">
                    <span>조회수 : {{ attraction.readCount }}</span>
                    <!-- <span class="fw-bold">★ 5.0</span> -->
                    <div class="dropdown">
                      <button class="dropbtn btn btn-primary btn-outline">+</button>
                      <div class="dropdown-content">
                        <div v-for="(day, index) in days" :key="index">
                          <button
                            @click="registPlanList(attraction.contentId, day)"
                            class="dropdown-btn my-1 mx-1 scale-on-hover"
                            style="border-radius: 20px; width: 70px"
                          >
                            {{ day }} 일차
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- Card - end -->
          </div>
        </div>
      </div>
      <div v-show="!attrTab">
        <div class="row" style="width: 100%">
          <div
            class="col-12 col-sm-12 col-lg-12 col-xl-12 mb-4"
            v-for="(attraction, index) in favorites"
            :key="attraction.contentId"
          >
            <!-- Card - start -->
            <div
              class="card h-100 button-hover"
              @click="sendCardDetail(attraction)"
              style="width: 325px"
            >
              <div class="card-body d-flex flex-row align-items-center">
                <!-- Move the image outside the card-body -->
                <div style="height: 100px; width: 100px">
                  <img
                    :src="attraction.firstImage || notAttractionImageUrl"
                    class="card-img-top"
                    alt="..."
                    style="height: 100px; width: 100px; object-fit: cover"
                  />
                </div>

                <!-- Other card elements -->
                <div class="ml-3 flex-grow-1 mx-2">
                  <p class="card-title fs-6">{{ attraction.title }}</p>
                  <!-- Additional content -->
                  <p class="card-content">{{ attraction.addr1 }}</p>
                  <div class="d-flex justify-content-between align-items-center">
                    <span>조회수 : {{ attraction.readCount }}</span>
                    <!-- <span class="fw-bold">★ 5.0</span> -->
                    <div class="dropdown">
                      <button class="dropbtn btn btn-primary btn-outline">+</button>
                      <div class="dropdown-content">
                        <div v-for="(day, index) in days" :key="index">
                          <button
                            @click="registPlanList(attraction.contentId, day)"
                            class="dropdown-btn my-1 mx-1 scale-on-hover"
                            style="border-radius: 50px; width: 100px"
                          >
                            {{ day }} 일차
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- Card - end -->
          </div>
        </div>
      </div>
    </div>
    <!-- Main content -->
  </div>

  <!-- sidebar-plan-detail -->
  <div class="sidebar-plan-detail" v-show="isSidebarPlanDetailOpen">
    <!-- planDetail start -->
    <div class="container mt-3 mb-5">
      <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">여행 일정</div>

      <div class="mt-3">
        <h3>{{ planDetailStore.planTitle }}</h3>
        <p>{{ planDetailStore.content }}</p>
        <p>예산: {{ planDetailStore.budget }} 만원</p>
        <p>여행 기간: {{ planDetailStore.startDate }} ~ {{ planDetailStore.endDate }}</p>
      </div>

      <div class="row" style="width: 100%">
        <!-- Loop through unique day values -->
        <div v-for="(day, index) in uniqueDays" :key="index">
          <!-- Filter plans for the current day -->
          <!-- Display day header outside the second v-for loop -->
          <h3>{{ day }} 일차</h3>
          <div v-for="(plan, planIndex) in planDetailStore.list" :key="planIndex">
            <div class="col-12 col-sm-12 col-lg-12 col-xl-12 mb-3">
              <div v-if="plan.day === day">
                <!-- Card - start -->
                <div class="card h-100" style="width: 325px">
                  <div class="card-body d-flex flex-row align-items-center">
                    <div style="height: 100px; width: 100px">
                      <img
                        :src="plan.firstImage || notAttractionImageUrl"
                        class="card-img-top"
                        alt="..."
                        style="height: 100px; width: 100px; object-fit: cover"
                      />
                    </div>

                    <div class="ml-3 flex-grow-1 mx-2">
                      <h5 class="card-title fs-6">{{ plan.title }}</h5>
                      <p class="card-text">{{ plan.addr1 }}</p>
                      <div class="d-flex justify-content-between align-items-center">
                        <span>조회수 : {{ plan.readCount }}</span>
                        <a
                          href="#"
                          class="btn btn-primary btn-outline"
                          @click="deletePlanList(plan.planListId)"
                          >-</a
                        >
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Card - end -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div style="height: 70px"></div>
    <!-- planDetail end -->
  </div>
  <!-- sidebar-plan-detail -->
  <div
    id="map"
    class="map"
    :style="{
      width: isPlanDetailShown ? '70%' : '70%',
      left: isPlanDetailShown ? '1000px' : '600px',
      bottom: isPlanDetailShown ? '-55px' : '-55px',
      height: isPlanDetailShown ? '859px' : '859px'
    }"
  ></div>

  <transition name="fade">
    <PlanUpdateModal
      v-show="isPlanUpdateModalVisible"
      @close="isPlanUpdateModalVisible = false"
      @refresh="refreshPage"
    />
  </transition>
  <transition name="fade">
    <PlanRegisterModal
      v-show="isPlanRegisterModalVisible"
      @close="isPlanRegisterModalVisible = false"
      @refresh="refreshPage"
    />
  </transition>
</template>

<style scoped>
/* Add styles for the sidebar */
.sidebar .nav-item {
  font-size: 1.25rem;
  /* 글자 크기를 키움 */
  text-align: center;
  /* 텍스트를 가운데 정렬 */
  display: block;
  /* 링크를 블록 요소로 만들어 전체 너비를 차지하게 함 */
  padding: 0.5rem 0;
  /* 위, 아래에 패딩을 추가하여 더 클릭하기 쉽게 만듦 */
  margin: 0.5rem 0;
  /* 위, 아래 마진을 추가하여 시각적으로 분리 */
}

.sidebar {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  position: fixed;
  top: 55px;
  left: 0px;
  /* top: 50%;
  left: 600px;
  transform: translateY(-50%); */
  padding: 15px;
  background-color: #ffffff;
  width: 200px;
  /* Adjust the width as needed */
  height: 100%;
  overflow: auto;
  transition: transform 0.3s ease-in-out;
  z-index: 1;
  box-sizing: border-box;
  border: 1px solid #ccc;
  /* Border added */
}

.sidebar-plan,
.sidebar-plan-search {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  position: fixed;
  top: 55px;
  left: 200px;
  padding: 15px;
  background-color: #ffffff;
  width: 400px;
  /* Adjust the width as needed */
  height: 100%;
  overflow: auto;
  transition: transform 0.3s ease-in-out;
  z-index: 1;
  box-sizing: border-box;
  border: 1px solid #ccc;
  /* Border added */
}

.sidebar-plan-detail {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  position: fixed;
  top: 55px;
  left: 600px;
  padding: 15px;
  background-color: #ffffff;
  width: 400px;
  /* Adjust the width as needed */
  height: 100%;
  overflow: auto;
  transition: transform 0.3s ease-in-out;
  z-index: 1;
  box-sizing: border-box;
  border: 1px solid #ccc;
  /* Border added */
}

.sidebar-closed {
  transform: translateX(-100%);
}

.sidebar-open {
  transform: translateY(0);
}

/* Add styles for the main content area */
.main-content {
  padding: 16px;
  transition: margin 0.3s ease-in-out;
}

.content-expanded {
  margin-left: 200px;
  /* Adjust the margin to accommodate the sidebar width */
}

.content-collapsed {
  margin-left: 0;
}

.card {
  border: 1px solid grey;
  transition: border 0.3s;
  /* 부드러운 효과를 위한 트랜지션 추가 */
}

.card:hover {
  border: 2px solid blue;
}

.card:active {
  border: 2px solid red;
}

/* 추가된 스타일 */
.btn-primary {
  background-color: #007bff;
  color: #fff;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-warning {
  background-color: #ffc107;
  color: #212529;
}

.btn-warning:hover {
  background-color: #d39e00;
}

.btn-danger {
  background-color: #dc3545;
  color: #fff;
}

.btn-danger:hover {
  background-color: #c82333;
}

/* Additional styles for card layout */
.card-body {
  display: flex;
}

.card-img-top {
  width: 80px;
  height: 80px;
  object-fit: cover;
  /* Ensure the image covers the entire container */
}

.card-title {
  font-size: 1.25rem;
  /* Adjust the font size as needed */
  font-weight: bold;
}

/* Add styles for equal height */
.card-body {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.card-title,
.d-flex span {
  flex: 1;
  /* Distribute available space equally */
}

/* 드롭 다운 CSS */
.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  z-index: 1;
  font-weight: 10;
  min-width: 80px;
}

.dropdown-btn {
  background-color: white;
  border: 1px solid #ccc;
  /* border-radius: 20px; */
}

.dropdown:hover .dropdown-content {
  display: block;
}
/* 드롭 다운 CSS */

.nav-link.active {
  background-color: #007bff;
  color: #fff;
  font-weight: bold;
  font-size: 25px;
  transition: all 0.3s ease-in-out; /* Apply transition to all properties over 0.3 seconds */

  /* Add a box shadow to create a pop-out effect */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  transform: translateY(-2px); /* Move the element slightly upward */
}

.nav-link.active:hover {
  /* Add a slight hover effect for additional emphasis */
  transform: translateY(-3px); /* Move the element slightly more upward on hover */
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.5); /* Increase box shadow on hover */
}

.nav-link {
  transition: all 0.3s ease-in-out; /* Apply the same transition to all properties */
}

.nav-link:hover {
  /* Add a slight hover effect for non-active links */
  transform: translateY(-1px); /* Move the element slightly upward on hover */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); /* Apply a subtle box shadow on hover */
}

.scale-on-hover {
  transition: transform 0.3s ease-in-out;
}

.scale-on-hover:hover {
  transform: scale(1.1);
  color: green;
  font-weight: bold;
}
</style>
