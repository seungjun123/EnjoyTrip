<script setup>
import http from '@/common/axios.js'
import { ref, onMounted } from 'vue'
const emit = defineEmits(['close', 'refresh'])

const planTitle = ref('')
const content = ref('')
const budget = ref('')
const startDate = ref('')
const endDate = ref('')

const planRegist = async () => {
  // 여행 계획 저장 로직을 추가
  let planObj = {
    userSeq: sessionStorage.getItem('userSeq'),
    planTitle: planTitle.value,
    content: content.value,
    budget: budget.value,
    startDate: startDate.value,
    endDate: endDate.value
  }

  try {
    const { data } = await http.post('/plan', planObj)
    console.log(data)
    if (data.result == 'success') {
      alert('여행 계획이 저장되었습니다.')
      emit('refresh')
    }
    // 저장 후 모달 닫기
    closeModal()
  } catch (error) {
    console.log(error)
  }
}

function closeModal() {
  console.log('close')
  emit('close')
}
</script>

<template>
  <!-- 모달 -->
  <div class="modal fade show" tabindex="-1" role="dialog" style="display: block">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">여행 계획 만들기</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
            @click="closeModal"
          ></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="planRegist">
            <div class="mb-3">
              <label for="title" class="form-label">제목</label>
              <input v-model="planTitle" type="text" class="form-control" id="title" required />
            </div>
            <div class="mb-3">
              <label for="content" class="form-label">내용</label>
              <textarea
                v-model="content"
                class="form-control"
                id="content"
                rows="3"
                required
              ></textarea>
            </div>
            <div class="mb-3">
              <label for="budget" class="form-label">예산</label>
              <input v-model="budget" type="number" class="form-control" id="budget" required />
            </div>
            <div class="mb-3">
              <label for="reg_dt" class="form-label">여행 시작 일자</label>
              <input v-model="startDate" type="date" class="form-control" id="reg_dt" required />
            </div>
            <div class="mb-3">
              <label for="reg_dt" class="form-label">여행 종료 일자</label>
              <input v-model="endDate" type="date" class="form-control" id="reg_dt" required />
            </div>
            <button type="submit" class="btn btn-primary">저장</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 여기에 필요한 스타일 추가 */
</style>
