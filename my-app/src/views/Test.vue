<template>
  <div>
    {{info}}
  </div>
</template>

<script>

export default {
  name: 'Test',
  data () {
    return {
      info: null
    }
  },
  mounted () {
    this.testCollectionGet()
  },
  methods:{
    testCollectionGet(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Collection/Get',
        data: {
          account: 'abcd',
        },
        crossDomain: true
      }).then(body => {
        this.info = body
      });
    },
    testCollectionAdd(){
      var i = 0
      for (i = 0; i < this.$store.state.collection.length; i++) {
        console.log('id',this.$store.state.collection[i].id)
        console.log('filmName',this.$store.state.collection[i].filmName)
        console.log('score',this.$store.state.collection[i].score)
        console.log('account',this.$store.state.collection[i].account)
        this.axios({
          method: 'post',
          url: 'http://114.115.151.96:8666/Collection/Add',
          data: {
            id: this.$store.state.collection[i].id,
            filmName: this.$store.state.collection[i].filmName,
            score: this.$store.state.collection[i].score,
            account: this.$store.state.collection[i].account,
          },
          crossDomain: true
        }).then(body => {
          this.info = body
        });
      }
    }
  }

}
</script>

<style scoped>

</style>
