<template>
  <div style="height: 70px"></div>
  <div class="container mt-4">
    <div class="row justify-content-center">
      <div class="card border-0 shadow-sm">
        <div class="card-body">
          <div class="d-flex justify-content-between">
            <div>
              <h5 class="card-title fs-4 fw-bold">{{ name }}</h5>
              <p class="card-subtitle text-muted mb-2 mt-3">
                작성자 : {{ userId }}
                <small class="mx-3">작성일시 : {{ date }} {{ time }}</small>
              </p>
            </div>
            <button
              type="button"
              class="btn btn-primary mt-5"
              style="width: 20%; height: 100%"
              @click="startChat"
            >
              동행 채팅 참여하기
            </button>
          </div>

          <!-- 첨부파일 일단 보류
          <div v-if="boardStore.fileList.length > 0">
            <hr />
            <div v-for="(file, index) in boardStore.fileList" :key="index">
              <span class="fileName">{{ file.fileName }}</span>
              <a
                class="btn btn-outline-secondary btn-sm mx-3 my-1"
                :href="file.fileUrl"
                :download="file.fileName"
                >내려받기</a
              >
            </div>
          </div> -->
          <hr />
          <!-- 내용 들어감 -->
          <p class="card-text mb-4">
            <span class="d-block mt-2" v-html="content" style="height: 40vh"></span>
          </p>
        </div>
        <!-- <div v-if="boardStore.sameUser">
          <div class="card-footer text-end">
            <button @click="changeToUpdate" class="btn btn-primary btn-sm mx-3" type="button">
              글 수정하기
            </button>
            <button @click="changeToDelete" class="btn btn-danger btn-sm" type="button">
              글 삭제하기
            </button>
          </div>
        </div> -->
      </div>
    </div>
  </div>
</template>

<script setup>
import { inject, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import util from '@/common/util.js'
import http from '@/common/axios.js'

const route = useRoute()
const router = useRouter()

const id = ref('')
const content = ref('')
const name = ref('')
const roomId = ref('')
const userId = ref('')
const userName = ref('')
const userSeq = ref('')
const regDt = ref('')
const date = ref('')
const time = ref('')

id.value = route.query.id
content.value = route.query.content
name.value = route.query.name
roomId.value = route.query.roomId
userId.value = route.query.userId
userName.value = route.query.userName
userSeq.value = route.query.userSeq
regDt.value = route.query.regDt
date.value = route.query.date
time.value = route.query.time

const startChat = async () => {
  // 그룹 채팅방을 만들고 거기에 바로 입장

  router.push({
    name: 'chatting',
    query: {
      id: id.value,
      content: content.value,
      name: name.value,
      roomId: roomId.value,
      userId: userId.value,
      userName: userName.value,
      userSeq: userSeq.value,
      date: date.value,
      time: time.value
    }
  })
}
</script>

<style scoped></style>
