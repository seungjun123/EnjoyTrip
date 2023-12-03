import { reactive, computed } from 'vue'
import { defineStore } from 'pinia'

import http from '@/common/axios.js'

export const useBoardStore = defineStore('boardStore', () => {
  const boardStore = reactive({
    // list
    list: [],
    limit: 10,
    offset: 0,
    searchWord: '',

    // pagination
    listRowCount: 10,
    pageLinkCount: 10,
    currentPageIndex: 1,
    totalListItemCount: 0,

    // detail, update, delete
    boardId: 0,
    title: '',
    content: '',
    userName: '',
    regDate: '',
    regTime: '',
    readCount: 0,
    fileList: [],
    sameUser: false
  })

  const setBoardList = (list) => (boardStore.list = list)
  const setTotalListItemCount = (count) => (boardStore.totalListItemCount = count)
  const setBoardMovePage = (pageIndex) => {
    boardStore.offset = (pageIndex - 1) * boardStore.listRowCount
    boardStore.currentPageIndex = pageIndex
  }
  const setBoardDetail = (payload) => {
    boardStore.boardId = payload.boardId
    boardStore.title = payload.title
    boardStore.content = payload.content
    boardStore.userName = payload.userName
    boardStore.regDate = payload.regDt.date
    console.log(boardStore.regDate)
    boardStore.regTime = payload.regDt.time
    boardStore.readCount = payload.readCount
    boardStore.fileList = payload.fileList
    boardStore.sameUser = payload.sameUser
  }

  // list
  const boardList = async () => {
    let params = {
      limit: boardStore.limit,
      offset: boardStore.offset,
      searchWord: boardStore.searchWord
    }

    try {
      let { data } = await http.get('/boards', { params }) // params: params shorthand property, let response 도 제거
      //  if (data.result == "login") {
      //     router.push("/login");
      //  } else {
      setBoardList(data.list)
      setTotalListItemCount(data.count)
      //}
    } catch (error) {
      console.error(error)
    }
  }

  // list
  const myBoardList = async (userSeq) => {
    let params = {
      limit: boardStore.limit,
      offset: boardStore.offset,
      searchWord: boardStore.searchWord,
      userSeq: userSeq
    }

    try {
      let { data } = await http.get('/myBoards', { params }) // params: params shorthand property, let response 도 제거
      //  if (data.result == "login") {
      //     router.push("/login");
      //  } else {
      setBoardList(data.list)
      setTotalListItemCount(data.count)
      //}
    } catch (error) {
      console.error(error)
    }
  }

  // pagination
  const pageCount = computed(() =>
    Math.ceil(boardStore.totalListItemCount / boardStore.listRowCount)
  )
  const startPageIndex = computed(() => {
    if (boardStore.currentPageIndex % boardStore.pageLinkCount == 0) {
      //10, 20...맨마지막
      return (
        (boardStore.currentPageIndex / boardStore.pageLinkCount - 1) * boardStore.pageLinkCount + 1
      )
    } else {
      return (
        Math.floor(boardStore.currentPageIndex / boardStore.pageLinkCount) *
        boardStore.pageLinkCount +
        1
      )
    }
  })

  const endPageIndex = computed(() => {
    if (boardStore.currentPageIndex % boardStore.pageLinkCount == 0) {
      //10, 20...맨마지막
      return (
        (boardStore.currentPageIndex / boardStore.pageLinkCount - 1) * boardStore.pageLinkCount +
        boardStore.pageLinkCount
      )
    } else {
      return (
        Math.floor(boardStore.currentPageIndex / boardStore.pageLinkCount) *
        boardStore.pageLinkCount +
        boardStore.pageLinkCount
      )
    }
  })

  const prev = computed(() =>
    boardStore.currentPageIndex <= boardStore.pageLinkCount ? false : true
  )
  const next = computed(() =>
    Math.floor(pageCount / boardStore.pageLinkCount) * boardStore.pageLinkCount <
      boardStore.currentPageIndex
      ? false
      : true
  )

  return {
    boardStore,
    setBoardList,
    setBoardMovePage,
    setTotalListItemCount,
    setBoardMovePage,
    setBoardDetail,
    boardList,
    myBoardList,
    pageCount,
    startPageIndex,
    endPageIndex,
    prev,
    next
  }
})
