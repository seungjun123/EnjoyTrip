<template>
    <div style="height: 100px"></div>
    <div class="container">
        <h4 class="text-center">나의 게시글</h4>

        <board-list @call-parent-show-detail="showDetailModal"></board-list>

        <PaginationUI v-on:call-parent="movePage"></PaginationUI>

    </div>
    <div style="height: 70px"></div>
</template>

<script setup>
import { onMounted } from 'vue'

// component
import PaginationUI from '@/components/board/PaginationUI.vue'

import BoardList from '@/components/board/BoardList.vue'

// router
import { useRouter } from 'vue-router'

// store
import { useBoardStore } from '@/stores/boardStore'

const router = useRouter()

const { boardList, setBoardMovePage, myBoardList } = useBoardStore()

// 초기 작업
myBoardList(sessionStorage.getItem('userSeq'))

// pagination
const movePage = (pageIndex) => {
    console.log('BoardMainVue : movePage : pageIndex : ' + pageIndex)
    setBoardMovePage(pageIndex)
    myBoardList(sessionStorage.getItem('userSeq'))
}

const showDetailModal = () => {
    // Modal.show 인데 router.push 로 변경
    //detailModal.show();

    router.push({
        name: 'boardDetail'
    })
}
</script>

<style scoped></style>
