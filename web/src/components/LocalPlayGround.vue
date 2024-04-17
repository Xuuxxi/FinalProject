<template>
  <div class="playground">
    <LocalGameMap style="padding:10px 18px 18px"></LocalGameMap>
  </div>
</template>

<script>
import LocalGameMap from './LocalGameMap.vue';

export default {
  components: { LocalGameMap }
}
</script>

<style scoped>
div.playground {
    width: 60vw;
    height: 70vh;
    margin: 40px auto;
    background-color: rgba(50, 50, 50, 0.5);
    border-radius: 2%;
}
</style>