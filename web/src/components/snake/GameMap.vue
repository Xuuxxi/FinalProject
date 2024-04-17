<template>
  <div class="row">
      <div class="col-1">
          <div class="Info">
              <div v-if="role === 'A'">
                  <img :src="$store.state.user.photo" alt="">
                  <button v-if="playByBot === 0" type="button" class="btn btn-success"
                      style="width: 100%; margin-top: 9px; border-radius: 10px;" @click="startBot">托管</button>
                  <button v-else type="button" class="btn btn-success"
                      style="width: 100%; margin-top: 9px; border-radius: 10px;" @click="stopBot">取消</button>
              </div>
              <div v-else><img :src="$store.state.pk.opponent_photo" alt=""></div>
          </div>
      </div>
      <div class="col-1">
          <div class="lInfo">
              <div v-if="role === 'A'">
                  <div class="username">{{ $store.state.user.username }}</div>
              </div>
              <div v-else>
                  <div class="username">{{ $store.state.pk.opponent_username }}</div>
              </div>
              <div class="rating">{{ $store.state.pk.a_rating }}</div>
          </div>
      </div>
      <div ref="parent" class="gamemap col-8">
          <canvas ref="canvas" tabindex="0"></canvas>
      </div>
      <div class="col-1">
          <div class="rInfo">
              <div v-if="role === 'B'">
                  <div class="Rusername">{{ $store.state.user.username }}</div>
              </div>
              <div v-else>
                  <div class="Rusername">{{ $store.state.pk.opponent_username }}</div>
              </div>
              <div class="Rrating">{{ $store.state.pk.b_rating }}</div>
          </div>
      </div>
      <div class="col-1">
          <div class="Info">
              <div v-if="role === 'B'">
                  <img :src="$store.state.user.photo" alt="">
                  <button v-if="playByBot === 0" type="button" class="btn btn-success"
                      style="width: 100%; margin-top: 9px; border-radius: 10px;" @click="startBot">托管</button>
                  <button v-else type="button" class="btn btn-success"
                      style="width: 100%; margin-top: 9px; border-radius: 10px;" @click="stopBot">取消</button>
              </div>
              <div v-else><img :src="$store.state.pk.opponent_photo" alt=""></div>
          </div>
      </div>
  </div>
</template>

<script>
import { GameMap } from "@/assets/scripts/GameMap";
import { ref, onMounted } from 'vue'
import { useStore } from "vuex";

export default {
  setup() {
      const store = useStore();
      let parent = ref(null);
      let canvas = ref(null);
      let playByBot = ref(0);

      let role = ref('');
      if (store.state.pk.a_id == store.state.user.id) role.value = "A";
      else role.value = "B";

      onMounted(() => {
          store.commit(
              "updateGameObject",
              new GameMap(canvas.value.getContext('2d'), parent.value, store)
          );
      });

      return {
          parent,
          canvas,
          playByBot,
          role
      }
  }
}
</script>

<style scoped>
div.gamemap {
  width: 700px;
  height: 700px;
  margin: 10% auto
}

div.role {
  text-align: center;
  color: antiquewhite;
  font-size: 50px;
  font-weight: 600;
}

img {
  width: 100%;
  border-radius: 25%;
  margin: auto;
}

div.username {
  font-size: 24px;
  font-weight: 600;
  color: black;
  text-align: left;
  margin: auto;
}

div.rating {
  font-size: 24px;
  font-weight: 600;
  color: antiquewhite;
  font-style: oblique;
  text-align: left;
  margin: auto;
}

div.Rusername {
  font-size: 24px;
  font-weight: 600;
  color: black;
  text-align: right;
}

div.Rrating {
  font-size: 24px;
  font-weight: 600;
  color: antiquewhite;
  font-style: oblique;
  text-align: right;
}
</style>
