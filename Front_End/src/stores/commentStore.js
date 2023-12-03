import http from '@/common/axios.js'
import { ref, computed, reactive } from 'vue'
import { defineStore } from 'pinia'

export const useCommentStore = defineStore('commentStore', () => {
  const commentStore = reactive({
    list: []
  })

  const getCommentList = async (boardId) => {
    try {
      const { data } = await http.get(`/comment/${boardId}`)
      commentStore.list = data
      console.log(commentStore.list)
    } catch (error) {
      console.log(error)
    }
  }

  return { commentStore, getCommentList }
})
