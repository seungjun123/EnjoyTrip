<script setup>
import http from '@/common/axios.js'
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import notAttractionImageUrl from '@/assets/img/noImage.png'
const route = useRoute()
const router = useRouter()
const { authStore, setLogout, updateUserInfo } = useAuthStore()

const userSeq = ref(route.query.userSeq)
const userId = ref()
const userPassword = ref()
const userPassword2 = ref()
const userEmail = ref()
const userProfileImageUrl = ref()

// focus
const isUserPasswordFocus = ref(false)
const isUserPassword2Focus = ref(false)
const isUserEmailFocus = ref(false)

// validation
const isUserPasswordValid = ref(false)
const isUserPassword2Valid = ref(false)
const isUserEmailValid = ref(false)

const isUserPasswordFocusAndValid = computed(
  () => isUserPasswordFocus.value && isUserPasswordValid.value
)
const isUserPasswordFocusAndInvalid = computed(
  () => isUserPasswordFocus.value && !isUserPasswordValid.value
)
const isUserPassword2FocusAndValid = computed(
  () => isUserPassword2Focus.value && isUserPassword2Valid.value
)
const isUserPassword2FocusAndInvalid = computed(
  () => isUserPassword2Focus.value && !isUserPassword2Valid.value
)
const isUserEmailFocusAndValid = computed(() => isUserEmailFocus.value && isUserEmailValid.value)
const isUserEmailFocusAndInValid = computed(() => isUserEmailFocus.value && !isUserEmailValid.value)

const validatePassword = () => {
  let patternEngAtListOne = new RegExp(/[a-zA-Z]+/) // + for at least one
  let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/) // + for at least one
  let patternNumAtListOne = new RegExp(/[0-9]+/) // + for at least one

  isUserPasswordValid.value =
    patternEngAtListOne.test(userPassword.value) &&
      patternSpeAtListOne.test(userPassword.value) &&
      patternNumAtListOne.test(userPassword.value) &&
      userPassword.value.length >= 8
      ? true
      : false
}
const validatePassword2 = () => {
  isUserPassword2Valid.value = userPassword.value == userPassword2.value ? true : false
}

const validateEmail = () => {
  // ^ 시작일치, $ 끝 일치
  // {2, 3} 2개 ~ 3개
  // * 0회 이상, + 1회 이상
  // [-_.] - 또는 _ 또는 .
  // ? 없거나 1회
  let regexp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
  isUserEmailValid.value = regexp.test(userEmail.value) ? true : false
  console.log(isUserEmailValid.value)
}

const getUserInfo = async () => {
  try {
    const { data } = await http.get(`users/${userSeq.value}`)
    console.log(data)
    userId.value = data.userId
    userEmail.value = data.userEmail
    userProfileImageUrl.value =
      data.userProfileImageUrl == undefined ? notAttractionImageUrl : authStore.userProfileImageUrl
    console.log(userProfileImageUrl.value)
  } catch (error) {
    console.log(error)
  }
}

getUserInfo()

const userInfoUpdate = async () => {
  console.log('userInfoUpdate')
  console.log(sessionStorage.getItem('userPassword'))
  console.log(userPassword.value)
  if (sessionStorage.getItem('userPassword') == userPassword.value) {
    alert('이전과 같은 비밀번호로는 변경이 불가능합니다.')
  } else {
    let userInfo = {
      userPassword: userPassword.value,
      userEmail: userEmail.value
    }

    try {
      const { data } = await http.put(`users/${userSeq.value}`, userInfo)

      if (data.result == 'success') {
        alert('회원 정보가 수정 되었습니다.')
        userPassword.value = ''
        userPassword2.value = ''
        updateUserInfo({
          userPassword: data.userPassword,
          userEmail: data.userEmail
        })
      }
    } catch (error) {
      console.log(error)
    }
  }
}

const userInfoDelete = async () => {
  try {
    const { data } = await http.delete(`users/${userSeq.value}`)

    if (data.result == 'success') {
      alert('회원 탈퇴 되었습니다.')
      setLogout()
      router.push('/')
    }
  } catch (error) {
    console.log(error)
  }
}

// 이미지 변경 로직
const onInputImage = () => {
  console.log($refs.serveyImage.files)
}

// 이미지 변경 로직
</script>

<template>
  <div style="height: 70px"></div>
  <div class="container mt-3">
    <h4 class="text-center">My Page</h4>

    <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>
    <div class="d-flex justify-content-center">
      <figure class="figure">
        <img :src="userProfileImageUrl" class="figure-img img-fluid rounded" alt="..." style="width: 500px" />
      </figure>
      <input ref="fileInput" type="file" style="display: none" @change="handleFileChange" />
      <div class="d-flex align-items-center">
        <form class="col-sm-12">
          <div class="row mb-3">
            <label for="inputEmail3" class="col-sm-5 col-form-label">Id</label>
            <div class="col-sm-12">
              <input v-model="userId" type="text" class="form-control" disabled />
            </div>
          </div>

          <div class="row mb-3">
            <label for="inputPassword3" class="col-sm-5 col-form-label">비밀번호</label>
            <div class="col-sm-12">
              <input v-model="userPassword" type="password" class="form-control" name="pw" :class="{
                'is-valid': isUserPasswordFocusAndValid,
                'is-invalid': isUserPasswordFocusAndInvalid
              }" @input="validatePassword" @focus="isUserPasswordFocus = true" />
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">
                1개 이상의 특수문자, 대소문자 및 숫자를 포함하고 8자리 이상이여야 합니다.
              </div>
            </div>
          </div>

          <div class="row mb-3">
            <label for="inputPassword3" class="col-sm-5 col-form-label">비밀번호 확인</label>
            <div class="col-sm-12">
              <input v-model="userPassword2" type="password" class="form-control" :class="{
                'is-valid': isUserPassword2FocusAndValid,
                'is-invalid': isUserPassword2FocusAndInvalid
              }" @input="validatePassword2" @focus="isUserPassword2Focus = true" />
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">비밀번호가 일치하지 않습니다.</div>
            </div>
          </div>

          <div class="row mb-3">
            <label for="inputPassword3" class="col-sm-5 col-form-label">이메일</label>
            <div class="col-sm-12">
              <input type="email" class="form-control" :class="{
                'is-valid': isUserEmailFocusAndValid,
                'is-invalid': isUserEmailFocusAndInValid
              }" v-model="userEmail" @input="validateEmail" @focus="isUserEmailFocus = true" />
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">올바른 Email 을 입력해 주세요.</div>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-sm-6">
              <!-- Left-aligned button -->
              <button @click.prevent="userInfoUpdate" type="submit" class="btn btn-primary btn-sm">
                수정하기
              </button>
            </div>
            <div class="col-sm-6 text-end">
              <!-- Right-aligned button -->
              <button @click.prevent="userInfoDelete" type="submit" class="btn btn-outline-danger btn-sm">
                탈퇴하기
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
