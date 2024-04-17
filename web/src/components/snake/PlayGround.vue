<template>
  <div class="playground">
    <GameMap></GameMap>
  </div>
</template>

<script>
import GameMap from '@/components/snake/GameMap.vue';
export default {
  components: { GameMap },

  setup() {
    
  }
}
</script>

<style>
div.playground {
  width: 60vw;
  height: 70vh;
  margin: 40px auto;
}
</style>