<template>
  <div style="height: 70px"></div>
  <div class="container mt-3">
    <div class="row" style="width: 100%">
      <div
        class="col-12 col-sm-6 col-lg-4 col-xl-3 mb-4"
        v-for="(ex, index) in chatStore.list"
        :key="index"
      >
        <!-- Card start -->
        <div
          class="card button-hover"
          @click="sendCardDetail(ex)"
          style="height: 25vh"
          v-show="ex.sameUser"
        >
          <div class="d-flex my-2 justify-content-end" v-show="ex.sameUser">
            <button type="button" class="btn btn-danger mx-2" @click.stop="deleteChatRoom(ex.id)">
              X
            </button>
          </div>

          <div class="card-body d-flex flex-column">
            <h5 class="card-title">{{ ex.name }}</h5>
            <div v-html="ex.content" class="html-content"></div>

            <!-- 여기에 mt-auto 클래스를 추가하여 하단에 고정 -->
            <div class="d-flex justify-content-between my-1 mx-1">
              <span class="reviews">작성자 : {{ ex.userId }}</span>
              <span class="reviews">{{ util.makeDateStr(ex.regDt.date, '.') }}</span>
            </div>
          </div>
        </div>
        <!-- Card end -->
      </div>
    </div>
  </div>
  <div style="height: 70px"></div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import http from '@/common/axios.js'
import util from '@/common/util.js'
import notAttractionImageUrl from '@/assets/img/ha1.jpg'
import { useChatStore } from '@/stores/chatStore'

const { chatStore, myChatList, chatList, deleteChat } = useChatStore()

const router = useRouter()

const sendCardDetail = (ex) => {
  console.log('동행찾기 세부 카드 ', ex)
  router.push({
    name: 'findDetail',
    query: {
      id: ex.id,
      content: ex.content,
      name: ex.name,
      roomId: ex.roomId,
      userId: ex.userId,
      userName: ex.userName,
      userSeq: ex.userSeq,
      date: util.makeDateStr(ex.regDt.date, '.'),
      time: util.makeTimeStr(ex.regDt.time, ':')
    }
  })
}

const modifyChatRoom = (roomId) => {
  console.log('채팅방 수정')
}

const deleteChatRoom = async (roomId) => {
  if (confirm('삭제 하겠습니까 ?')) {
    console.log('채팅방 삭제')
    await deleteChat(roomId)
  }
  chatList()
}

// ============ 페이지 로드 될때마다 실행
myChatList()
</script>

<style scoped></style>
