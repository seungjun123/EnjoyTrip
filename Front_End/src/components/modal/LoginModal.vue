<template>
  <div
    class="modal fade show"
    data-bs-backdrop="static"
    data-bs-keyboard="false"
    tabindex="-1"
    aria-labelledby="staticBackdropLabel"
    style="display: block"
    aria-modal="true"
    role="dialog"
  >
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">
            <i class="bi bi-chat-left-dots-fill text-info"> LogIn</i>
          </h4>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            @click="closeModal"
          ></button>
        </div>

        <!-- Modal body -->
        <div class="modal-body">
          <form action="${root}/user">
            <input type="hidden" name="action" value="login" />
            <div class="mb-3">
              <label for="question" class="form-label">ID</label>
              <input
                v-model="authStore.userId"
                type="text"
                class="form-control"
                id="id"
                name="id"
              />
            </div>
            <div class="mb-3">
              <label for="question" class="form-label">PASSWORD</label>
              <input
                v-model="authStore.userPassword"
                type="password"
                class="form-control"
                id="password"
                name="pw"
              />
            </div>
            <div class="mb-3">{{ logoutFailMessage }}</div>
            <div class="modal-footer">
              <button @click.prevent="goRegisterModal" class="btn btn-primary btn-sm">
                회원가입
              </button>
              <button @click.prevent="goFindModal" class="btn btn-primary btn-sm">
                비밀번호 찾기
              </button>
              <button @click.prevent="login" class="btn btn-primary btn-sm">login</button>
              <button
                type="button"
                class="btn btn-outline-danger btn-sm"
                data-bs-dismiss="modal"
                @click="closeModal"
              >
                Close
              </button>
            </div>
          </form>
        </div>
        <!-- Modal footer -->
      </div>
    </div>
    <div class="modal-bg"></div>
  </div>
</template>

<script setup>
import http from '@/common/axios.js'
import { ref } from 'vue'
import { useAuthStore } from '../../stores/authStore'
const { authStore, setLogin } = useAuthStore()

const props = defineProps({
  isVisible: Boolean
})
const emit = defineEmits(['close', 'goFindPassword'])

const logoutFailMessage = ref()

const login = async () => {
  console.log(authStore.userId)
  console.log(authStore.userPassword)

  let params = {
    userId: authStore.userId,
    userPassword: authStore.userPassword
  }

  try {
    const { data } = await http.post('/users/login', null, { params })
    console.log(data)

    if (data.result == 'success') {
      // 세션 스토리지에 담는다. store 변경
      setLogin({
        isLogin: true,
        userSeq: data.userSeq,
        userName: data.userName,
        userPassword: data.userPassword,
        userProfileImageUrl: data.userProfileImageUrl
      })

      closeModal()
    } else {
      logoutFailMessage.value = '아이디 또는 비밀번호가 올바르지 않습니다. 다시 확인해주세요.'
    }
  } catch (error) {
    console.log(error)
  }
}

function goRegisterModal() {
  console.log('goRegisterModal')
  emit('goRegisterModal')
}

function goFindModal() {
  console.log('goFindPassword')
  emit('goFindPassword')
}

function closeModal() {
  console.log('close')
  emit('close', false)
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
