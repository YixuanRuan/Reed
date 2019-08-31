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
  methods: {
    testReport () {
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/report/list',
        data: {
        },
        crossDomain: true
      }).then(body => {
        this.info = body
      })
    },
    testLogin(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/User/Login',
        data: {
          account: 'czzssb',
          password: 'czzssb'
        },
        crossDomain: true
      }).then(body => {
        this.info = body
      })
    },
    testCollectionDel(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Collection/Del',
        data: {
          account: 'abcd'
        },
        crossDomain: true
      }).then(body => {
        this.info = body
      })
    },
    testCollectionGet(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Collection/Get',
        data: {
          account: 'abcd'
        },
        crossDomain: true
      }).then(body => {
        this.info = body
      });
    },
    testCollectionAdd(){
      var i = 0
      for (i = 0; i < this.$store.state.collection.length; i++) {
        this.axios({
          method: 'post',
          url: 'http://114.115.151.96:8666/Collection/Add',
          data: {
            id: this.$store.state.collection[i].id,
            filmName: this.$store.state.collection[i].filmName,
            score: this.$store.state.collection[i].score,
            account: 'abcd',
          },
          crossDomain: true
        }).then(body => {
          this.info = body
        })
      }
    },
    testInformationGet(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Information/Get',
        data: {
          account: 'abcd',
        },
        crossDomain: true
      }).then(body => {
        this.info = body
      });
    },
    testInformationAdd(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Information/Add',
        data: {
          postingId: ''
        },
        crossDomain: true
      }).then(body => {
        this.info = body
      });
    }
  }

}
</script>

<style scoped>

</style>
