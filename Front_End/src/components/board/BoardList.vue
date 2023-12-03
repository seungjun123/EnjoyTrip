<template>
  <table class="table table-hover">
    <thead>
      <tr>
        <th>#</th>
        <th>장소</th>
        <th>별점</th>
        <th>작성자</th>
        <th>작성일시</th>
        <th>조회수</th>
      </tr>
    </thead>
    <tbody>
      <!-- store 사용 -->

      <!-- 직접 store 에 접근해도 된다. -->
      <tr
        v-for="board in boardStore.list"
        @click="boardDetail(board.boardId)"
        v-bind:key="board.boardId"
      >
        <td>{{ board.boardId }}</td>
        <td>{{ board.title }}</td>
        <td>{{ board.rate }}</td>
        <td>{{ board.userName }}</td>
        <td>{{ util.makeDateStr(board.regDt.date, '.') }}</td>
        <td>{{ board.readCount }}</td>
      </tr>
    </tbody>
  </table>
</template>

<!-- 
  detailModal 을 show() 하기 위해 props 로 받아서 사용하면 null 이 넘어온다.
  BoardView 가 rendering 될 때 이미 null 로 전달됨. bootstrap 객체는 onMounted에서 null 이 아닌 modal 객체로 바뀜.
  props 로 받지 말고 emit 로 창을 띄우도록 하자
-->
<script setup>
import http from '@/common/axios.js'
import util from '@/common/util.js'
import { useBoardStore } from '@/stores/boardStore'
const { boardStore, setBoardDetail } = useBoardStore()

//  const props = defineProps(['detailModal'])
const emit = defineEmits(['call-parent-show-detail'])
const boardDetail = async (boardId) => {
  try {
    let { data } = await http.get('/boards/' + boardId)
    console.log(data)

    //  if (data.result == "login") {
    //     doLogout();
    //  } else {
    let { dto } = data
    setBoardDetail(dto)

    // props.detailModal.show();
    emit('call-parent-show-detail')
    //}
  } catch (error) {
    console.log('BoardMainVue: error : ')
    console.log(error)
  }
}
</script>
