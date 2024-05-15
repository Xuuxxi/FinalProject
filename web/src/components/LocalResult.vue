<template>
    <div class="result-board">
        <div class="result-board-text" v-if="$store.state.pk.loser === 'all'">
            平局
        </div>
        <div class="result-board-text" v-else>
            玩家{{$store.state.pk.loser}}胜利
        </div>
        <div v-if="$store.state.pk.a_score === 0 && $store.state.pk.b_score === 0">
            <div class="score_text">
                回合超时!
            </div>
        </div>
        <div v-else>
            <div class="score_text">
                玩家A分数: {{$store.state.pk.a_score}}
            </div>
            <div class="score_text">
                玩家B分数: {{$store.state.pk.b_score}}
            </div>
        </div>
        <div class="result-board-btn">
            <button @click="restart" type="button" class="btn btn-warning btn-lg">
                再来!
            </button>
        </div>
    </div>
</template>

<script>

export default {
    setup() {
        const restart = () => {
            location.reload();
        }

        return {
            restart
        };
    }
}
</script>

<style scoped>
div.result-board {
    height: 30vh;
    width: 30vw;
    border-radius: 20px;
    background-color: rgba(50, 50, 50, 0.55);
    position: absolute;
    top: 30vh;
    left: 35vw;
}

div.result-board-text {
    text-align: center;
    color: white;
    font-size: 50px;
    font-weight: 600;
    font-style: italic;
    padding-top: 2.5vh;
    padding-bottom: 1vh;
}

div.result-board-btn {
    padding-top: 3vh;
    text-align: center;
}

div.score_text {
    text-align: center;
    color: white;
    font-size: 30px;
    font-weight: 600;
}
</style>