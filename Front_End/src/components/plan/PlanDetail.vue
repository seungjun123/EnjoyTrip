<script setup>
import { onMounted, ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { usePlanStore } from '@/stores/planStore'
const route = useRoute()
const { planDetailStore, planDetail } = usePlanStore()

const curDay = ref(1)

onMounted(() => {
  planDetail(sessionStorage.getItem('planId'))
  // planDetail(route.query.planId)
  //   console.log(planDetailStore)
})

// Use a computed property to filter unique day values
const uniqueDays = computed(() => {
  const uniqueDaysSet = new Set(planDetailStore.list.map((plan) => plan.day));
  const maxDay = Math.max(...uniqueDaysSet); // Find the maximum day

  // Generate an array starting from 1 to the maximum day
  return Array.from({ length: maxDay }, (_, index) => index + 1);
});
</script>

<template>
  <!-- planDetail start -->
  <div style="height: 70px"></div>
  <div class="container mt-3">
    <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">여행 일정</div>

    <div class="mt-3">
      <h3>{{ planDetailStore.title }}</h3>
      <p>{{ planDetailStore.content }}</p>
      <p>예산: {{ planDetailStore.budget }}</p>
      <p>여행 기간: {{ planDetailStore.startDate }} ~ {{ planDetailStore.endDate }}</p>
      <h3>위치</h3>
    </div>

    <div class="row" style="width: 100%">
      <!-- Loop through unique day values -->
      <div v-for="(day, index) in uniqueDays" :key="index">
        <!-- Display day header outside the second v-for loop -->
        <h3>{{ day }} 일차</h3>
        <div class="d-flex flex-wrap mt-3">
          <!-- Filter plans for the current day -->
          <div v-for="(plan, planIndex) in planDetailStore.list" :key="planIndex">
            <div class="col-12 col-sm-8 col-lg-10 col-xl-10 mb-4">
              <div v-if="plan.day === day">
                <!-- Card - start -->
                <div class="card h-100">
                  <div class="card-body d-flex flex-column">
                    <h5 class="card-title">{{ day }} 일차</h5>
                    <p class="card-text">{{ plan.addr1 }}</p>
                    <div class="mt-auto">
                      <div class="d-flex justify-content-between">
                        <span>조회수 : dd</span>
                        <span class="fw-bold">★ 5.0</span>
                      </div>
                      <a href="#" class="btn btn-primary btn-outline mt-3" @click="sendCardDetail(attraction)">자세히</a>
                    </div>
                  </div>
                </div>
                <!-- Card - end -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div style="height: 70px"></div>
  <!-- planDetail end -->
</template>

<style scoped></style>
