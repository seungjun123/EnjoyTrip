<template>
  <div style="height: 70px"></div>
  <div class="container mt-3">
    <!-- 동행찾기 검색 start -->
    <div>
      <form class="d-flex my-3 justify-content-between" role="search" onsubmit="prevSubmit();">
        <button
          id="btn-search"
          class="btn btn-outline-primary col-sm-1 mx-5"
          type="submit"
          @click.prevent="showInsertModal"
        >
          글작성
        </button>
      </form>
    </div>
    <!-- 동행찾기 검색 end -->
    <findDHCard></findDHCard>
    <findDHinsertModal v-on:call-parent-fdhinsert="closeAfterInsert"></findDHinsertModal>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import findDHCard from '@/components/findDH/findDHCard.vue'
import findDHinsertModal from '@/components/findDH/modal/findDHinsertModal.vue'
import { useChatStore } from '@/stores/chatStore'
// bootstrap 객체 생성
import { Modal } from 'bootstrap'

const { chatStore, myChatList, chatList } = useChatStore()

let fDHinsertModal = null

// lifecycle
onMounted(() => {
  fDHinsertModal = new Modal(document.getElementById('findDHInsertModal'))
})

// insert
const showInsertModal = () => fDHinsertModal.show()
const closeAfterInsert = () => {
  fDHinsertModal.hide()
  //boardList()
  //등록후에 동행찾기 list 다시 불러오기
  chatList()
}
</script>
<style scoped></style>
