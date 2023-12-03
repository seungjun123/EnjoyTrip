<template>
  <div style="height: 70px"></div>
  <div class="container mt-3">
    <div class="row" style="width: 100%">
      <div
        class="col-12 col-sm-6 col-lg-4 col-xl-3 mb-4"
        v-for="(attraction, index) in favorites"
        :key="attraction.contentId"
      >
        <!-- Card - start -->
        <div class="card h-100 button-hover" @click="sendCardDetail(attraction)">
          <div style="height: 50%">
            <img
              :src="attraction.firstImage || notAttractionImageUrl"
              class="card-img-top"
              alt="..."
              style="height: 100%"
            />
          </div>

          <div class="card-body d-flex flex-column">
            <h5 class="card-title">{{ attraction.title }}</h5>
            <p class="card-text">{{ attraction.addr1 }}</p>
            <div class="mt-auto">
              <div class="d-flex justify-content-between">
                <!-- <span>조회수 : {{ attraction.readCount }}</span>
              <span class="fw-bold">★ 5.0</span> -->
                <span></span>
                <span @click.stop="toggleFavorite(attraction.contentId, attraction.userSeq)" style="cursor: pointer">
                  <font-awesome-icon
                    v-if="attraction.userSeq"
                    icon="fa-solid fa-heart"
                    class="fa-2x"
                    style="color: red"
                  />
                  <font-awesome-icon
                    v-else
                    icon="fa-regular fa-heart"
                    class="fa-2x"
                    style="color: black"
                  />
                </span>
              </div>
              <!-- <a href="#" class="btn btn-primary btn-outline mt-3" @click="sendCardDetail(attraction)">자세히</a> -->
            </div>
          </div>
        </div>
        <!-- Card - end -->
      </div>
    </div>
  </div>
  <div style="height: 70px"></div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import http from '@/common/axios.js'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const { authStore } = useAuthStore()

const router = useRouter()

const favorites = ref('')

const favoriteList = async () => {
  try {
    let { data } = await http.get('/users/favorite') // {params:params} 의 shorthand property
    console.log(data)
    favorites.value = data.attrlist

  } catch (error) {
    console.log(error)
  }
}

const sendCardDetail = (attractionDetail) => {
  console.log(attractionDetail)

  router.push({
    name: 'cardDetail',
    query: {
      contentId: attractionDetail.contentId,
      title: attractionDetail.title,
      addr1: attractionDetail.addr1,
      first_image: attractionDetail.firstImage,
      first_image2: attractionDetail.firstImage2,
      readcount: attractionDetail.readCount,
      overview: attractionDetail.overview
    }
  })
}


const toggleFavorite = async (attractionId , userSeq) => {
  console.log('즐겨찾기 클릭함 ' + attractionId)

  // 로그인 된 상태에서만
  if (authStore.isLogin) {
    if(userSeq){ // 이미 즐겨찾기 추가한 상태이므로 delete 수행
      await favoriteDelete(attractionId)
    }else{
      await favoriteInsert(attractionId)
    }

    favoriteList()

  } else {
    alert('로그인 하세요')
  }
}

const favoriteInsert = async (attractionId) => {
  // insert 요청 날리기
  try {
    let { data } = await http.post(`/users/favorite?contentId=${attractionId}`)
    console.log(data)
  } catch (error) {
    console.log('favorite insert : ' + error)
  }
}

const favoriteDelete = async (attractionId) => {
  // delete 요청 날리기
  try {
    let { data } = await http.delete(`/users/favorite?contentId=${attractionId}`)
    console.log(data)
  } catch (error) {
    console.log('favorite insert : ' + error)
  }
}

// =========================== 
favoriteList()
</script>

<style scoped></style>
