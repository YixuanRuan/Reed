<template>
  <v-sheet
    class="mx-auto"
    height="100%"
    max-width="100%"

    style="background: transparent"
  >
    <div style="min-height: 250px; width: 100%;">
      <v-slide-group
        v-model="model"
        class="pa-4 slide-c "
        active-class="success"
        show-arrows
      >
        <draggable class="row wrap justify-space-around" :list="card_content" group="people">
            <v-flex
              v-for="(data, index) in card_content"
              :key="index"
            >
              <div @click="getBMId(data)">
              <InfoCard style="margin-right: 2px" :poster_img="$store.state.movie_img_prefix+data.id" :name="data.filmName"
                        :score="data.score" :director="data.director" :Starring="data.starring"
                        :duration="data.filmTime" :brief="data.intro" :looked="data.looked" />
              </div>
            </v-flex>
        </draggable>
      </v-slide-group>
    </div>
  </v-sheet>
</template>

<script>
import InfoCard from './InfoCard'
import draggable from 'vuedraggable'

export default {
  name: "HistoryComment",
  data: () => ({
    model: null
  }),
  methods:{
    getBMId: function (data) {
      this.$store.dispatch('changeCurrentBMId', data.id);
      this.$router.push({path: '/bmshow'})
    }
  },
  components: {
    InfoCard,
    draggable
  },
  props: [
    'card_content'
  ]
}
</script>

<style scoped>
</style>
