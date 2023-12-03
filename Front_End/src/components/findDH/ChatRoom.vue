<template>
  <div style="height: 70px"></div>
  <div class="container-fluid">
    <div class="row">
      <!-- 참여자 목록 start -->
      <div class="col-md-3">
        <div class="users-list bg-light p-3 my-1 mx-2" style="height: 79vh; overflow-auto;">
          <h5 class="text-center">참여중</h5>
          <hr />
          <ul class="list-unstyled">
            <li v-for="user in users.list" :key="user.id" class="mb-2">
              <div class="user d-flex align-items-center">
                <div class="user-avatar me-2">
                  <!-- 아바타 이미지 또는 사용자명의 첫 글자를 표시 -->
                  ✔️
                </div>
                <div class="user-info">
                  <strong>{{ user.userId }}</strong>
                  <!-- 추가 정보 표시 -->
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <!-- 참여자 목록 end -->
      <!-- 채팅창 start -->
      <div class="col-md-9">
        <div class="chat-container d-flex flex-column mx-2 my-1" style="height: 83vh">
          <div class="d-grid gap-2">
            <button class="btn btn-danger mx-5 my-2" type="button" @click="quit">방 나가기</button>
          </div>
          <div ref="messagesContainer" class="chat-messages flex-grow-1 overflow-auto">
            <!-- 메시지 틀 start -->
            <div class="message py-2 px-3" v-for="msg in allChatList.list" :key="msg.id">
              <div class="message-header">
                <strong>{{ msg.userId }}</strong>
                <small class="text-muted"
                  >{{ util.makeDateStr(msg.regDt.date, '.') }}
                  {{ util.makeTimeStr(msg.regDt.time, ':') }}</small
                >
              </div>
              <div class="message-body">{{ msg.message }}</div>
            </div>
            <!-- 메시지 틀 end -->
          </div>
          <div class="input-group" style="width: 100%">
            <input
              type="text"
              class="form-control"
              placeholder="메시지 입력"
              v-model="newMessage"
              @keyup.enter="sendMessage"
            />
            <button class="btn btn-secondary" type="button" @click="sendMessage">전송</button>
          </div>
        </div>
      </div>
      <!-- 채팅창 end -->
    </div>
  </div>

  <div style="height: 70px"></div>
</template>

<script setup>
import { inject, onMounted, ref, reactive, nextTick, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import http from '@/common/axios.js'
import util from '@/common/util.js'
import { onMessage, onOpen, onClose, onError } from 'vue3-websocket'
import router from '../../router'
import { useAuthStore } from '@/stores/authStore'

const route = useRoute()
const { authStore } = useAuthStore()

const id = ref('')
const content = ref('')
const name = ref('')
const roomId = ref(route.query.roomId)
const userId = ref('')
const userName = ref('')
const userSeq = ref('')
const regDt = ref('')
const date = ref('')
const time = ref('')

const newMessage = ref('')
const messages = ref([])
const messagesContainer = ref(null)

const text = ref('')
const responseMsg = ref('')
const socket = inject('socket')

const allChatList = reactive({
  list: []
})

const users = reactive({
  list: []
})

// ===============================================
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

onOpen((obj) => {
  console.log(obj)
  console.log('WS connection is stable! ~uWu~')

  enterRoom(socket)
})

onMessage((message) => {
  console.log('Got a message from the WS: ', message)

  // 누군가가 메시지를 보냈다는 뜻
  // db 의 메시지를 들고와서 뿌려줌
  msgList()
  chatPeopleList()
})

onClose((obj) => {
  console.log(obj)
  console.log('No way, connection has been closed 😥')
})

onError((error) => {
  console.error('Error: ', error)
})

const enterRoom = () => {
  var enterMsg = {
    id: id.value,
    type: 'ENTER',
    roomId: roomId.value,
    sender: authStore.userName,
    message: '',
    userSeq: authStore.userSeq
  } //sender는  글쓸때 수정하자.
  console.log(socket.value.readyState)
  try {
    socket.value.send(JSON.stringify(enterMsg))
  } catch (error) {}

  insertPeople()
}

const sendMessage = () => {
  if (newMessage.value.trim() !== '') {
    // const newMsg = {
    //   id: Date.now(), // 임시 ID
    //   sender: '사용자명', // 실제 사용자명으로 대체
    //   time: new Date().toLocaleTimeString(), // 실제 시간 형식으로 대체
    //   text: newMessage.value
    // }
    // messages.value.push(newMsg)
    // newMessage.value = '' // 입력 필드 초기화

    var talkMsg = {
      id: id.value,
      type: 'TALK',
      roomId: roomId.value,
      sender: authStore.userName,
      message: newMessage.value,
      userSeq: authStore.userSeq
    }
    socket.value.send(JSON.stringify(talkMsg))
  }
  newMessage.value = ''
}

const quit = () => {
  var quitMsg = {
    id: id.value,
    type: 'QUIT',
    roomId: roomId.value,
    sender: authStore.userName,
    msg: '',
    userSeq: authStore.userSeq
  }
  socket.value.send(JSON.stringify(quitMsg))
  socket.value.close()
  //location.href = '/chat/chatList'

  deletePeople()

  router.push({
    name: 'finddh'
  })
}

const insertPeople = async () => {
  try {
    let { data } = await http.post('/chat/people/' + id.value)
    console.log('사용자 채팅방 insert 성공 ', data)
  } catch (error) {
    console.log('사용자 채팅방 insert 실패')
    console.log(error)
  }
}

const deletePeople = async () => {
  try {
    let { data } = await http.delete('/chat/people/' + id.value)
    console.log('사용자 채팅방 delete 성공 ', data)
  } catch (error) {
    console.log('사용자 채팅방 delete 실패')
    console.log(error)
  }
}

const chatPeopleList = async () => {
  try {
    let { data } = await http.get('/chat/people/' + id.value)
    users.list = data.list
    console.log('사용자 채팅방 목록 성공 ', data)
  } catch (error) {
    console.log('사용자 채팅방 목록 실패')
    console.log(error)
  }
}

// 메시지의 목록을 뿌려줌
const msgList = async () => {
  try {
    let { data } = await http.get('/chat/' + id.value) // {params:params} 의 shorthand property
    //console.log('chatting목록 받아오기 ', data)
    allChatList.list = data.msgList
  } catch (error) {
    console.log(error)
  }
  scrollToBottom()
}

const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}

// 메시지 배열에 변화가 있을 때마다 스크롤을 아래로 이동시킵니다.
watch(messages, scrollToBottom)

//========================================== 로드될때
enterRoom()
msgList()
chatPeopleList()
</script>

<style scoped>
/* 이전 스타일 그대로 유지 */
.chat-container {
  height: 500px; /* 채팅 인터페이스의 높이를 조정하세요 */
  border: 1px solid #dee2e6;
  border-radius: 0.375rem;
}

.chat-messages {
  padding: 1rem;
  background-color: #f8f9fa;
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.message-body {
  margin-top: 0.375rem;
  padding: 0.375rem 0.75rem;
  background-color: #fff;
  border-radius: 0.375rem;
}

.input-group {
  padding: 0.375rem;
}
</style>
