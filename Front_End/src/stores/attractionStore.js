import { reactive } from 'vue'
import { defineStore } from 'pinia'
import http from '@/common/axios.js'

export const useAttractionStore = defineStore('attractionStore', () => {
  const attractionStore = reactive({
    limit: 12,
    offset: 0,
    searchWord: '',
    list: [],
    totalPages: 10
  })

  // setter 만들어줌
  const setAttractionList = (list) => (attractionStore.list = list)

  const attractionList = async () => {
    let params = {
      limit: attractionStore.limit,
      offset: attractionStore.offset
    }
    try {
      let { data } = await http.get('/attraction', { params }) // {params:params} 의 shorthand property
      console.log('attraction 요청 ', data)

      setAttractionList(data.list)
    } catch (error) {
      console.log(error)
    }
  }

  const attractionListLike = async (limit) => {
    let params = {
      limit: limit,
      offset: attractionStore.offset,
      searchWord: attractionStore.searchWord
    }
    try {
      let { data } = await http.get('/attraction/search', { params }) // {params:params} 의 shorthand property
      console.log('attraction/search 요청 ', data)

      setAttractionList(data.list)
    } catch (error) {
      console.log(error)
    }
  }

  return { attractionStore, attractionList, attractionListLike }
})
