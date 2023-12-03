<script setup>
import http from '@/common/axios.js'
import { usePlanStore } from '@/stores/planStore'
const { planDetailStore } = usePlanStore()
const emit = defineEmits(['close', 'refresh'])

const planUpdate = async () => {
  let planObj = {
    userSeq: sessionStorage.getItem('userSeq'),
    planTitle: planDetailStore.planTitle,
    content: planDetailStore.content,
    budget: planDetailStore.budget,
    startDate: planDetailStore.startDate,
    endDate: planDetailStore.endDate,
    planId: planDetailStore.planId
  }

  try {
    const { data } = await http.put('/plan', planObj)
    console.log(data)
    if (data.result == 'success') {
      alert('여행 계획이 변경되었습니다.')
      emit('refresh')
      // 저장 후 모달 닫기
      closeModal()
    } else {
      alert('여행 계획 변경이 실패하였습니다.')
    }
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
          <h5 class="modal-title">여행 계획 수정하기</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
            @click="closeModal"
          ></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="planUpdate">
            <div class="mb-3">
              <label for="title" class="form-label">제목</label>
              <input
                v-model="planDetailStore.planTitle"
                type="text"
                class="form-control"
                id="title"
                required
              />
            </div>
            <div class="mb-3">
              <label for="content" class="form-label">내용</label>
              <textarea
                v-model="planDetailStore.content"
                class="form-control"
                id="content"
                rows="3"
                required
              ></textarea>
            </div>
            <div class="mb-3">
              <label for="budget" class="form-label">예산</label>
              <input
                v-model="planDetailStore.budget"
                type="number"
                class="form-control"
                id="budget"
                required
              />
            </div>
            <div class="mb-3">
              <label for="reg_dt" class="form-label">여행 시작 일자</label>
              <input
                v-model="planDetailStore.startDate"
                type="date"
                class="form-control"
                id="reg_dt"
                required
              />
            </div>
            <div class="mb-3">
              <label for="reg_dt" class="form-label">여행 종료 일자</label>
              <input
                v-model="planDetailStore.endDate"
                type="date"
                class="form-control"
                id="reg_dt"
                required
              />
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
