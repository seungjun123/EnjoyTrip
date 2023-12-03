<template>
  <div
    class="modal fade show"
    id="sujeong"
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
            <i class="bi bi-chat-left-dots-fill text-info"> 회원 정보 수정</i>
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
            <input type="hidden" name="action" value="update" />
            <input type="hidden" name="id" value="${userInfo.id}" />

            <div class="mb-3">
              <label for="question" class="form-label">이름</label>
              <input type="text" class="form-control" id="question" name="name" />
            </div>

            <div class="mb-3">
              <label for="question" class="form-label">비밀번호</label>
              <input type="password" class="form-control" id="question" name="pw" />
            </div>

            <div class="mb-3">
              <label for="question" class="form-label">이메일</label>
              <input type="email" class="form-control" id="question" name="email" />
            </div>

            <div class="modal-footer">
              <button type="submit" id="gaipbtn" class="btn btn-primary btn-sm">
                회원 정보 수정
              </button>

              <button type="reset" id="gaipbtn" class="btn btn-primary btn-sm">초기화</button>

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
      </div>
    </div>
    <div class="modal-bg"></div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  isVisible: Boolean
})

const emit = defineEmits(['close'])

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
