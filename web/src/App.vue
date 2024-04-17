<template>
  <NavBar></NavBar>
  <router-view />
</template>

<script>
import NavBar from './components/NavBar.vue';
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
// import $ from 'jquery'
export default {
  components: { NavBar },
  // setup() {
  //   let isChecked = ref(0);
  //   let dice_num = ref(1);

  //   const clickDice = () => {
  //     isChecked.value = (isChecked.value + 1) % 2;
  //     console.log(isChecked.value);
  //   }

  //   return {
  //     dice_num,
  //     isChecked,
  //     clickDice
  //   }
  // }
  // setup(){
  //   $.ajax({
  //     url: 'http://localhost:3000/user/account/info/',
  //     type: 'get',
  //     headers: {
  //       Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4NzA0YjJmOTE4ZjQ0NjhkOGFiNmZjYWQ3YWE4NzI1NyIsInN1YiI6IjU4Mzg5Mjk5NiIsImlzcyI6InNnIiwiaWF0IjoxNjYzMDY3ODE3LCJleHAiOjE2NjQyNzc0MTd9.fa4PWDJtfH1wlT2rBXvSVMHJNijQESBJyL9bzRXS2U0"
  //     },
  //     data: {
  //       username: 'xuuxxi',
  //       password: '123456',
  //     },
  //     success(resp){
  //       console.log(resp)
  //     },
  //     error(resp){
  //       console.log(resp)
  //     }
  //   })
  // }
}
</script>

<style>
body {
  background-image: url("@/assets/images/background.jpg");
  background-size: cover;
}
</style>
