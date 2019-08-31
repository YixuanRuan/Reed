<template>
  <v-sheet
    class="mx-auto"
    height="100%"
    max-width="100%"
    style="background: transparent"
  >
    <div style="min-height: 250px">
      <v-slide-group
        v-model="model"
        class="pa-4 silde-c"
        active-class="success"
        show-arrows
      >
        <draggable class="row wrap justify-space-around" :list="card_content" group="people">

            <v-flex
              v-for="(data, index) in card_content"
              :key="index"
            >
              <div @click="getBMId(data)">
                <BookCard style="margin-right: 2px" :poster_img="img_prefix+data.id" :name="data.bookName"
                          :score="data.score" :author="data.author" :publisher="data.publisher"
                          :isbn="data.isbn" :intro="data.intro" :price="data.price" :id="data.id"/>
              </div>
            </v-flex>
        </draggable>
      </v-slide-group>
    </div>
  </v-sheet>
</template>

<script>
import BookCard from './BookCard'
import draggable from 'vuedraggable'

export default {
  name: "BookComment",
  data: () => ({
    model: null,
    img_prefix:'http://114.115.151.96:8666/PosterPicture/MovieAccount/'
  }),
  methods:{
    getBMId: function (data) {
      this.$store.dispatch('changeCurrentBMId', data.id);
      this.$router.push({path: '/bmshow'})
    }
  },
  components: {
    BookCard,
    draggable
  },
  props: [
    'card_content'
  ]
}
</script>

<style scoped>
</style>
