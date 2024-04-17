<template>
    <div class="playground">
        <GameMap style="padding:10px 18px 18px"></GameMap>
    </div>
</template>
  
<script>
import GameMap from './GameMap.vue';

export default {
    components: { GameMap },

    setup() {

    }
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