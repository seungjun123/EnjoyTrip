<template>
  <div class="modal" id="gaipModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
    aria-labelledby="staticBackdropLabel" style="display: block" aria-modal="true" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">
            <i class="bi bi-chat-left-dots-fill text-info"> 회원가입</i>
          </h4>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal"></button>
        </div>

        <!-- Modal body -->
        <div class="modal-body">
          <form action="${root}/user">
            <input type="hidden" name="action" value="regist" />

            <div class="mb-3">
              <label for="question" class="form-label">id</label>
              <input type="text" class="form-control" name="id"
                :class="{ 'is-valid': isUserIdFocusAndValid, 'is-invalid': isUserIdFocusAndInvalid }" v-model="userId"
                @input="validateUserId" @focus="isUserIdFocus = true" />
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">올바른 Id를 입력해 주세요.</div>
            </div>

            <div class="mb-3">
              <label for="question" class="form-label">이름</label>
              <input type="text" class="form-control" name="name"
                :class="{ 'is-valid': isUserNameFocusAndValid, 'is-invalid': isUserNameFocusAndInvalid }"
                v-model="userName" @input="validateUserName" @focus="isUserNameFocus = true" />
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">올바른 이름을 입력해 주세요.</div>
            </div>
            <div class="mb-3">
              <label for="question" class="form-label">비밀번호</label>
              <input type="password" class="form-control" name="pw"
                :class="{ 'is-valid': isUserPasswordFocusAndValid, 'is-invalid': isUserPasswordFocusAndInvalid }"
                v-model="userPassword" @input="validatePassword" @focus="isUserPasswordFocus = true" />
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">1개 이상의 특수문자, 대소문자 및 숫자를 포함하고 8자리 이상이여야 합니다.</div>
            </div>

            <div class="mb-3">
              <label for="question" class="form-label">비밀번호 확인</label>
              <input type="password" class="form-control" name="question"
                :class="{ 'is-valid': isUserPassword2FocusAndValid, 'is-invalid': isUserPassword2FocusAndInvalid }"
                v-model="userPassword2" @input="validatePassword2" @focus="isUserPassword2Focus = true" />
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">비밀번호가 일치하지 않습니다.</div>
            </div>

            <div class="mb-3">
              <label for="question" class="form-label">이메일</label>
              <input type="email" class="form-control" name="email"
                :class="{ 'is-valid': isUserEmailFocusAndValid, 'is-invalid': isUserEmailFocusAndInValid }"
                v-model="userEmail" @input="validateEmail" @focus="isUserEmailFocus = true" />
              <!-- 테스트 코드 -->
              <!-- {{isUserEmailFocus}} - {{ isUserEmailFocusAndValid }} - {{ isUserEmailFocusAndInValid }} -->
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">올바른 Email 을 입력해 주세요.</div>
            </div>

            <div class="modal-footer">
              <button type="submit" class="btn btn-primary btn-sm" @click.prevent="register">
                회원가입
              </button>

              <button type="reset" class="btn btn-primary btn-sm">초기화</button>

              <button type="button" class="btn btn-outline-danger btn-sm" data-bs-dismiss="modal" @click="closeModal">
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
import { ref, computed } from 'vue'

const props = defineProps({
  isVisible: Boolean
})

const emit = defineEmits(['close', 'goLoginModal'])

const userId = ref()
const userName = ref()
const userPassword = ref()
const userPassword2 = ref()
const userEmail = ref()

// focus
const isUserIdFocus = ref(false)
const isUserNameFocus = ref(false)
const isUserEmailFocus = ref(false)
const isUserPasswordFocus = ref(false)
const isUserPassword2Focus = ref(false)

// validation
const isUserIdValid = ref(false)
const isUserNameValid = ref(false)
const isUserEmailValid = ref(false)
const isUserPasswordValid = ref(false)
const isUserPassword2Valid = ref(false)

const isUserIdFocusAndValid = computed(() => isUserIdFocus.value && isUserIdValid.value)
const isUserIdFocusAndInvalid = computed(() => isUserIdFocus.value && !isUserIdValid.value)
const isUserNameFocusAndValid = computed(() => isUserNameFocus.value && isUserNameValid.value)
const isUserNameFocusAndInvalid = computed(() => isUserNameFocus.value && !isUserNameValid.value)
const isUserEmailFocusAndValid = computed(() => isUserEmailFocus.value && isUserEmailValid.value)
const isUserEmailFocusAndInValid = computed(() => isUserEmailFocus.value && !isUserEmailValid.value)
const isUserPasswordFocusAndValid = computed(() => isUserPasswordFocus.value && isUserPasswordValid.value)
const isUserPasswordFocusAndInvalid = computed(() => isUserPasswordFocus.value && !isUserPasswordValid.value)
const isUserPassword2FocusAndValid = computed(() => isUserPassword2Focus.value && isUserPassword2Valid.value)
const isUserPassword2FocusAndInvalid = computed(() => isUserPassword2Focus.value && !isUserPassword2Valid.value)

const validateUserId = () => {
  isUserIdValid.value = userId.value.length > 0 ? true : false;
  console.log(isUserIdValid.value);
}

const validateUserName = () => {
  isUserNameValid.value = userName.value.length > 0 ? true : false;
  console.log(isUserNameValid.value);
}

const validateEmail = () => {
  // ^ 시작일치, $ 끝 일치
  // {2, 3} 2개 ~ 3개
  // * 0회 이상, + 1회 이상
  // [-_.] - 또는 _ 또는 .
  // ? 없거나 1회
  let regexp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
  isUserEmailValid.value = regexp.test(userEmail.value) ? true : false;
  console.log(isUserEmailValid.value);
}
const validatePassword = () => {
  let patternEngAtListOne = new RegExp(/[a-zA-Z]+/); // + for at least one
  let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/); // + for at least one
  let patternNumAtListOne = new RegExp(/[0-9]+/); // + for at least one

  isUserPasswordValid.value =
    patternEngAtListOne.test(userPassword.value) &&
      patternSpeAtListOne.test(userPassword.value) &&
      patternNumAtListOne.test(userPassword.value) &&
      userPassword.value.length >= 8 ? true : false;
}
const validatePassword2 = () => {
  isUserPassword2Valid.value = userPassword.value == userPassword2.value ? true : false;
}

const register = async () => {
  let userObj = {
    userId: userId.value,
    userName: userName.value,
    userPassword: userPassword.value,
    userEmail: userEmail.value
  }

  console.log(userObj)
  try {
    const { data } = await http.post('/users', userObj)
    console.log(data)
    if (data.result == 'success') {
      alert('회원가입 성공!')
      goLoginModal()
    } else {
      alert('회원가입 실패!')
    }
  } catch (error) {
    console.log(error)
  }
}

function goLoginModal() {
  console.log('goLoginModal')
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
