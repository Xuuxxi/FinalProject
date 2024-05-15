<template>
    <div class="result-board">
        <div class="result-board-text" v-if="$store.state.pk.loser === 'all'">
            平局
        </div>
        <div class="result-board-text" v-else-if="$store.state.pk.loser === 'A' && $store.state.pk.a_id === parseInt($store.state.user.id)">
            你输了
            <div class="rank_text">RANK分 -5</div>
        </div>
        <div class="result-board-text" v-else-if="$store.state.pk.loser === 'B' && $store.state.pk.b_id === parseInt($store.state.user.id)">
            你输了
            <div class="rank_text">RANK分 -5</div>
        </div>
        <div class="result-board-text" v-else>
            你赢了！
            <div class="rank_text">RANK分 +5!</div>
        </div>
        <div class="result-board-btn">
            <button @click="restart" type="button" class="btn btn-warning btn-lg">
                再来!
            </button>
        </div>
    </div>    
</template>

<script>
import { useStore } from 'vuex';

export default {
    setup() {
        const store = useStore();

        const restart = () => {
            store.commit("updateStatus", "matching");
            store.commit("updateLoser", "none");
            store.commit("updateOpponent", {
                username: "待匹配",
                photo: "https://img1.baidu.com/it/u=3373990605,1380325608&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1664470800&t=69ba588c27005cd51ffab1a02cb7d94c",
            })
        }

        return {
            restart
        };
    }
}
</script>

<style scoped>
div.result-board {
    height: 25vh;
    width: 20vw;
    background-color: rgba(50, 50, 50, 0.55);
    border-radius: 20px;
    /* position: absolute;
    top: 28vh;
    left: 40.5vw; */
}

div.result-board-text {
    text-align: center;
    color: white;
    font-size: 50px;
    font-weight: 600;
    font-style: italic;
    padding-top: 2.5vh;
    padding-bottom: 2vh;
}

div.result-board-btn {
    padding-top: 1vh;
    text-align: center;
}

div.rank_text {
    text-align: center;
    color: antiquewhite;
    font-size: 25px;
    font-weight: 400;
    padding-top: 2vh;
}
</style>