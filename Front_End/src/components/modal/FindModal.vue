<template>
  <div class="modal fade show" id="findModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
    aria-labelledby="staticBackdropLabel" style="display: block" aria-modal="true" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">
            <i class="bi bi-chat-left-dots-fill text-info">비밀번호 찾기</i>
          </h4>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal"></button>
        </div>

        <!-- Modal body -->
        <div class="modal-body">
          <form action="${root}/user">
            <input type="hidden" name="action" value="find" />
            <div class="mb-3">
              <div class="mb-3">
                <label for="question" class="form-label">아이디</label>
                <input v-model="userId" type="text" class="form-control" id="question" name="id" />
              </div>

              <label for="question" class="form-label">이름</label>
              <input v-model="userName" type="text" class="form-control" id="question" name="name" />
            </div>

            <!-- <div class="mb-3">
              <label for="question" class="form-label">이메일</label>
              <input type="email" class="form-control" id="question" name="email" />
            </div> -->

            <div class="mb-3">{{ findFailMessage }}</div>

            <div class="modal-footer">
              <button @click.prevent="findPassword" type="submit" id="gaipbtn" class="btn btn-primary btn-sm">
                비밀번호 찾기
              </button>

              <button @click.prevent="goLoginModal" type="submit" id="gaipbtn" class="btn btn-primary btn-sm">
                로그인 하러 가기
              </button>

              <!-- <button @click.prevent="reset" id="gaipbtn" class="btn btn-primary btn-sm">
                초기화
              </button> -->

              <button type="button" class="btn btn-outline-danger btn-sm" data-bs-dismiss="modal" @click="closeModal">
                Close
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="modal-bg"></div>
  </div>
</template>

<script setup>
import http from '@/common/axios'
import { ref } from 'vue'

const props = defineProps({
  isVisible: Boolean
})

const emit = defineEmits(['close', 'goLoginModal'])

const userId = ref('')
const userName = ref('')
// const userEmail = ref()
const findFailMessage = ref()

const findPassword = async () => {
  let params = {
    userId: userId.value,
    userName: userName.value
  }

  try {
    const { data } = await http.post('/users/find', null, { params })
    console.log(data)
    console.log(data.userPassword)
    if (data.userPassword == undefined) {
      findFailMessage.value = '비밀번호를 찾을 수 없습니다.'
    } else {
      findFailMessage.value = data.userName + '님의 비밀번호는 ' + data.userPassword + '입니다.'
    }
  } catch (error) {
    findFailMessage.value = '아이디와 이름을 입력해주세요.'
    console.log(error)
  }
}

const reset = () => {
  userId.value = ''
  userName.value = ''
  findFailMessage.value = ''
}

const goLoginModal = () => {
  emit('goLoginModal')
}

function closeModal() {
  console.log('close')
  emit('close')
}
</script>

<style scoped>
.modal-bg {
  z-index: -1;
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100vh;
  background-color: rgba(224, 224, 224, 0.5);
}
</style>
