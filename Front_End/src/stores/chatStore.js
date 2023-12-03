import { ref, computed, reactive } from 'vue'
import { defineStore } from 'pinia'
import http from '@/common/axios.js'
import { useRouter } from 'vue-router'

// store 에서 router 를 사용하려면 defineStore 안에 선언
export const useChatStore = defineStore('chatStore', () => {
  const router = useRouter()

  const chatStore = reactive({
    limit: 12,
    offset: 0,
    searchWord: '',
    list: [],
    totalPages: 10,

    // detail, update, delete
    // boardId: 0,
    // title: '',
    // content: '',
    // userName: '',
    // regDate: '',
    // regTime: '',
    // readCount: 0,
    // fileList: [],
    sameUser: false
  })

  // setter 만들어줌
  const setChatList = (list) => (chatStore.list = list)

  const chatList = async () => {
    let params = {
      limit: chatStore.limit,
      offset: chatStore.offset
    }
    try {
      let { data } = await http.get('/chat/list') // {params:params} 의 shorthand property
      console.log('채팅방 리스트 요청 ', data)

      setChatList(data.list)
    } catch (error) {
      console.log(error)
    }
  }

  const myChatList = async (limit) => {
    let params = {
      limit: limit,
      offset: chatStore.offset,
      searchWord: chatStore.searchWord
    }
    try {
      let { data } = await http.get('/chat/mypage')
      console.log('chat/mypage', data)

      setChatList(data.list)
    } catch (error) {
      console.log(error)
    }
  }

  const deleteChat = async (roomId) => {
    console.log('삭제할려는 방 roomID ', roomId)
    try {
      let { data } = await http.delete('/chat/' + roomId)
      console.log('채팅방 지우기 성공', data)
    } catch (error) {
      console.log(error)
    }
  }

  return { chatStore, myChatList, chatList, deleteChat }
})
