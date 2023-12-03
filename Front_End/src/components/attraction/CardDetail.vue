<template>
  <section class="blog-details spad">
    <div style="height: 70px"></div>
    <div class="container">
      <div class="row d-flex justify-content-center">
        <div class="col-lg-8">
          <div class="blog__details__title">
            <div class="blog__details__social">
              <img :src="first_image" style="width: 100%" />
            </div>
            <h2 class="mt-5">{{ title }}</h2>
            <h5 class="mt-3 mb-3">주소 : {{ addr1 }}</h5>
          </div>
        </div>
        <div class="col-lg-8">
          <div class="blog__details__content">
            <div class="blog__details__text">
              <p>
                {{ overview }}
              </p>
            </div>
            <hr class="mt-5 mb-5" />
            <div class="blog__details__item__text">
              <h3>위치</h3>
              <div id="map" class="map" style="width: 100%; height: 350px"></div>
  
            </div>
            <hr class="mt-5 mb-4" />
            <h3 class="mb-5">후기 게시판</h3>
            <div class="blog__details__item__text d-flex align-items-center">
              <h3 class="mb-0 me-3" style="font-size: 80px;">⭐ {{ total_rate }}</h3>
              <button type="button" class="btn btn-hover" style="border-radius: 10px; border: 1px solid #000; margin-left:20%" @click="sendCommunity">
                후기 {{ total_opinion }}개 모두 보기
              </button>
            </div>

            <div style="height: 70px"></div>
          </div>
        </div>
      </div>
    </div>
    <div style="height:70px;"></div>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRoute , useRouter } from 'vue-router'
import http from "@/common/axios.js";
import {useBoardStore} from '@/stores/boardStore'

const {boardStore} = useBoardStore();

const route = useRoute()
const router = useRouter()

const contentId = ref('')
const title = ref('')
const addr1 = ref('')
const first_image = ref('')
const first_image2 = ref('')
const readcount = ref('')
const overview = ref('')

const total_rate = ref('')
const total_opinion = ref('')

contentId.value = route.query.contentId
title.value = route.query.title
addr1.value = route.query.addr1
first_image.value = route.query.first_image
first_image2.value = route.query.first_image2
readcount.value = route.query.readcount
overview.value = route.query.overview

// -- kakao map Start --
let mapRef = null

onMounted(() => {
  const mapContainer = document.querySelector('#map') // 지도를 표시할 div
  console.log('찍혀 ??')
  console.log(mapContainer)

  const mapOption = {
    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
  }

  // 지도를 생성합니다
  const map = new kakao.maps.Map(mapContainer, mapOption)

  // 장소 검색 객체를 생성합니다
  const ps = new kakao.maps.services.Places()

  // 키워드로 장소를 검색합니다
  ps.keywordSearch(addr1.value, placesSearchCB)

  // 검색 완료 시 호출되는 콜백함수
  function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
      var bounds = new kakao.maps.LatLngBounds()

      displayMarker(data[0])
      bounds.extend(new kakao.maps.LatLng(data[0].y, data[0].x))

      map.setBounds(bounds)
    }
  }

  // 마커를 표시하는 함수입니다
  function displayMarker(place) {
    var marker = new kakao.maps.Marker({
      map: map,
      position: new kakao.maps.LatLng(place.y, place.x)
    })

    kakao.maps.event.addListener(marker, 'click', function () {
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 })
      infowindow.setContent(
        '<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>'
      )
      infowindow.open(map, marker)
    })
  }
})

// -- kakao map End --

const getRate = async () => {
  try {
    //console.log("ㅎㅇㅎㅎㅇㅎ" + contentId.value)
      let { data } = await http.get("/boards/avg?searchWord=" + contentId.value);
      //console.log(data);

      total_rate.value = data.avg
      total_opinion.value = data.count

  } catch (error) {
      console.log("CardDetail.vue error : ");
      console.log(error);
  }
}

const sendCommunity = () => {
  boardStore.searchWord = title
  router.push({
    name: 'board'
  })
}


// ======================= 페이지 로드 되면 해야할 것들

getRate()


// ========================================================
</script>
<style scoped>
.btn-hover:hover {
  background-color: #0a59f8; /* 마우스 오버 시 적용될 배경색, 원하는 색상으로 변경하세요 */
}
</style>
