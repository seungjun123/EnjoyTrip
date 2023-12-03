import http from '@/common/axios.js'
import { ref, computed, reactive } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'

export const usePlanStore = defineStore('planStore', () => {
  const planStore = reactive({
    limit: 10,
    offset: 0,
    list: [],
    totalPages: 10
  })

  const planDetailStore = reactive({
    planId: 0,
    userSeq: 0,
    planTitle: '',
    content: '',
    budget: 0,
    startDate: '',
    endDate: '',
    list: []
  })

  const plan = async (payload) => {
    try {
      let params = {
        limit: planStore.limit,
        offset: planStore.offset,
        userSeq: payload.userSeq
      }

      const { data } = await http.get('/plan', { params })
      if (data.result == 1) {
        planStore.list = data.list
      }
      console.log(planStore)
    } catch (error) {
      console.log(error)
    }
  }

  const planDetail = async (planId) => {
    try {
      const { data } = await http.get(`/plan/detail/${planId}`)
      if (data.result == 1) {
        planDetailStore.planId = data.dto.planId
        planDetailStore.userSeq = data.dto.userSeq
        planDetailStore.planTitle = data.dto.planTitle
        planDetailStore.content = data.dto.content
        planDetailStore.budget = data.dto.budget
        planDetailStore.startDate = data.dto.startDate
        planDetailStore.endDate = data.dto.endDate
        planDetailStore.list = data.attrList
        console.log(planDetailStore)
      }
    } catch (error) {
      console.log(error)
    }
  }

  const deletePlan = async (planId, planTitle, index) => {
    console.log('deletePlan')
    try {
      const { data } = await http.delete(`/plan/${planId}`)
      console.log(data)
      alert(planTitle + ' 여행 계획이 삭제 되었습니다.')

      planStore.list.splice(index, 1)
    } catch (error) {
      console.log(error)
    }
  }

  const planListRegist = async (payload) => {
    let planObj = {
      planId: payload.planId,
      contentId: payload.contentId,
      day: payload.day
    }

    // Check if contentId already exists in the planDetailStore list
    const isContentIdExists = planDetailStore.list.some(
      (plan) => plan.contentId === payload.contentId
    )

    if (isContentIdExists) {
      console.log('ContentId already exists in the list. Registration aborted.')
      if (!confirm('이미 여행 일정에 담겨 있는 여행지입니다. 그래도 방문하시겠습니까?')) {
        return // Stop execution if contentId already exists
      }
    }

    try {
      const { data } = await http.post('/plan/list', planObj)
      if (data.result == 'success') {
        console.log('추가 성공')
        planDetail(sessionStorage.getItem('planId'))
      }
      console.log(planDetailStore)
    } catch (error) {
      console.log(error)
    }
  }

  const planListDelete = async (planListId) => {
    try {
      const { data } = await http.delete(`/plan/list/${planListId}`)
      if (data.result == 'success') {
        console.log('삭제 성공')
        planDetail(sessionStorage.getItem('planId'))
      }
      console.log(planDetailStore)
    } catch (error) {
      console.log(error)
    }
  }

  return {
    planStore,
    planDetailStore,
    plan,
    planDetail,
    deletePlan,
    planListRegist,
    planListDelete
  }
})
