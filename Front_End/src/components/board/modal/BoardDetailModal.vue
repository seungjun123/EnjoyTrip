<template>
  <div style="height: 70px"></div>
  <div class="container mt-4">
    <div class="row justify-content-center">
      <div class="card border-0 shadow-sm">
        <div class="card-body">
          <h5 class="card-title fs-4 fw-bold">
            {{ boardStore.title }}
          </h5>
          <p class="card-subtitle text-muted mb-2 mt-3">
            {{ boardStore.userName }}
            <small class="mx-3">작성일시 {{ util.makeDateStr(boardStore.regDate, '/') }}
              {{ util.makeTimeStr(boardStore.regTime, ':') }}</small>
            <small>조회수 {{ boardStore.readCount }}</small>
          </p>
          <!-- 첨부파일 -->
          <div v-if="boardStore.fileList.length > 0">
            <hr />
            <div v-for="(file, index) in boardStore.fileList" :key="index">
              <span class="fileName">{{ file.fileName }}</span>
              <a class="btn btn-outline-secondary btn-sm mx-3 my-1" :href="file.fileUrl"
                :download="file.fileName">내려받기</a>
            </div>
          </div>
          <hr />
          <!-- 내용 들어감 -->
          <p class="card-text mb-4">
            <span class="d-block mt-2" v-html="boardStore.content" style="height: 100%"> </span>
          </p>
        </div>
        <div v-if="boardStore.sameUser">
          <div class="card-footer text-end">
            <button @click="changeToUpdate" class="btn btn-primary btn-sm mx-3" type="button">
              글 수정하기
            </button>
            <button @click="changeToDelete" class="btn btn-danger btn-sm" type="button">
              글 삭제하기
            </button>
          </div>
        </div>
        <div class="card-footer bg-transparent border-0 text-muted">
          <small>댓글 0</small>
        </div>
      </div>
    </div>
  </div>
  <!-- 댓글창 -->
  <div class="container-fluid bg-light p-0 my-5" style="margin-bottom: 20px">
    <div class="container py-4">
      <!-- 기타 컨텐츠 -->

      <!-- 댓글 입력창 -->
      <div class="row">
        <div class="col-12">
          <div class="bg-white p-3 shadow-sm rounded">
            <div class="d-flex align-items-center">
              <div class="flex-grow-1 me-3">
                <input v-model="commentContent" type="text" class="form-control"
                  :placeholder="`${authStore.userId}님, 댓글을 작성해보세요.`" />
              </div>
              <button @click="registComment" class="btn btn-primary">등록</button>
            </div>
          </div>
        </div>
      </div>

      <!-- 댓글 리스트 -->
      <div class="row mt-4">
        <div class="col-12">
          <div v-if="commentStore.list.length > 0">
            <div v-for="(comment, index) in commentStore.list" :key="index" class="mb-3">
              <div class="bg-white p-3 shadow-sm rounded">
                <!-- 댓글 내용 및 정보 표시 -->
                <p class="text-muted d-flex justify-content-between">
                  {{ comment.userId }}
                <div v-if="authStore.userSeq == comment.userSeq">
                  <button @click="editComment(comment)" class="btn btn-outline-primary btn-sm me-2">수정</button>
                  <button @click="deleteComment(comment)" class="btn btn-outline-danger btn-sm">삭제</button>
                </div>
                </p>
                <small class="text-muted d-flex justify-content-between">
                  {{ comment.commentContent }}
                  <span> 등록일 {{ comment.commentRegDt }} </span>
                </small>
                <div v-if="editMode && selectedComment === comment">
                  <input v-model="editedCommentContent" type="text" class="form-control mt-2" />
                  <button @click="updateComment(comment)" class="btn btn-primary btn-sm mt-2">저장</button>
                </div>
              </div>
            </div>
          </div>
          <div v-else>
            <p class="text-muted">댓글이 없습니다.</p>
          </div>
        </div>
      </div>
      <!-- 댓글 리스트 -->
    </div>
  </div>
</template>

<script setup>
import http from '@/common/axios.js'
import util from '@/common/util.js'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../../stores/authStore'
import { useBoardStore } from '@/stores/boardStore'
import { useCommentStore } from '../../../stores/commentStore'

const router = useRouter()
const { authStore } = useAuthStore()
const { boardStore } = useBoardStore()
const { commentStore, getCommentList } = useCommentStore()

const emit = defineEmits(['call-parent-change-to-update', 'call-parent-change-to-delete'])

onMounted(() => {
  getCommentList(boardStore.boardId)
  // console.log('onMounted : ' + commentStore.list)
})

let commentContent = ref()

const registComment = async () => {
  try {
    let currentDate = new Date()
    let formattedDate = `${currentDate.getFullYear()}-${(currentDate.getMonth() + 1).toString().padStart(2, '0')}-${currentDate.getDate().toString().padStart(2, '0')}`

    let commentObj = {
      boardId: boardStore.boardId,
      userSeq: authStore.userSeq,
      userId: authStore.userId,
      commentContent: commentContent.value,
      commentRegDt: formattedDate
    }

    const { data } = await http.post('/comment', commentObj)
    console.log(data)
    getCommentList(boardStore.boardId);
  } catch (error) {
    console.log(error)
  }
}

let editMode = ref(false);
let selectedComment = ref(null);
let editedCommentContent = ref('');

const editComment = (comment) => {
  // Set the selected comment and enable edit mode
  selectedComment.value = comment;
  editedCommentContent.value = comment.commentContent;
  editMode.value = true;
};

const updateComment = async (comment) => {
  console.log('updateComment')
  try {
    let currentDate = new Date()
    let formattedDate = `${currentDate.getFullYear()}-${(currentDate.getMonth() + 1).toString().padStart(2, '0')}-${currentDate.getDate().toString().padStart(2, '0')}`

    let commentObj = {
      commentId: comment.commentId,
      commentContent: editedCommentContent.value,
      commentRegDt: formattedDate
    }

    const { data } = await http.put('/comment', commentObj)
    console.log(data)
    getCommentList(boardStore.boardId);
  } catch (error) {
    console.log(error)
  }
}

const deleteComment = async (comment) => {
  console.log('deleteComment')
  try {
    const { data } = await http.delete(`/comment/${comment.commentId}`)
    console.log(data)
    getCommentList(boardStore.boardId);
  } catch (error) {
    console.log(error)
  }
}

// const changeToUpdate = () => emit('call-parent-change-to-update')
const changeToDelete = () => {
  if (confirm('이 글을 삭제하시겠습니까?')) {
    console.log('이제 삭제하러 가볼꼐요')
    boardDelete()
    router.push({
      name: 'board'
    })
  }
}

const boardDelete = async () => {
  try {
    let { data } = await http.delete('/boards/' + boardStore.boardId)
    console.log(data)

    if (data.result == 'login') {
      doLogout()
    } else {
      alert('글이 삭제되었습니다.')
      boardList()
    }
  } catch (error) {
    console.log('BoardMainVue: error : ')
    console.log(error)
  }
}

const changeToUpdate = () => {
  router.push({
    name: 'boardUpdate'
  })
}
</script>

<style scoped>
.container {
  max-width: 960px;
}

.fileName {
  word-break: break-all;
}
</style>
