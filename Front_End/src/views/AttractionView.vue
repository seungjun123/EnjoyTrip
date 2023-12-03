<template>
  <div>
    <!-- Main content -->
    <div class="main-content">
      <div style="height: 70px"></div>
      <div class="container mt-3">
        <!-- mt-3 추가 -->

        <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">
          전국 관광지 정보
        </div>

        <!-- 관광지 검색 start -->
        <form class="d-flex my-3 justify-content-center" role="search" onsubmit="prevSubmit();">
          <input
            id="search-keyword"
            class="form-control me-2 w-50"
            type="search"
            placeholder="검색어"
            aria-label="검색어"
            name="keyword"
            v-model="keyword"
          />
          <button
            id="btn-search"
            class="btn btn-outline-success col-sm-1"
            type="submit"
            @click="attractionSearch"
          >
            검색
          </button>
        </form>
        <!-- 관광지 검색 end -->
        <Card></Card>

        <PaginationUI></PaginationUI>
      </div>
      <div style="height: 70px"></div>
    </div>
    <!-- Main content -->
  </div>
</template>

<script setup>
import { ref } from 'vue'
import Card from '@/components/attraction/Card.vue'
import PaginationUI from '@/components/attraction/PaginationUI.vue'
import { useAttractionStore } from '@/stores/attractionStore'

const keyword = ref('')

const { attractionStore, attractionListLike } = useAttractionStore()

const attractionSearch = (e) => {
  e.preventDefault()

  attractionStore.searchWord = keyword.value
  attractionListLike(12)
}
</script>

<style scoped></style>
