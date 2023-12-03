<template>
  <nav aria-label="Page navigation example">
    <ul class="pagination">
      <li
        class="page-item"
        v-for="offset in attractionStore.totalPages"
        @click="changePage(offset)"
      >
        <a class="page-link" href="#">{{ offset }}</a>
      </li>
    </ul>
  </nav>
</template>

<script setup>
import { useAttractionStore } from '@/stores/attractionStore'

const { attractionStore, attractionListLike } = useAttractionStore()

const changePage = (offset) => {
  attractionStore.offset = offset * attractionStore.limit
  attractionListLike(12)
}
</script>
