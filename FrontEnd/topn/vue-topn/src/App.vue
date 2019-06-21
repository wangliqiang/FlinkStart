<template>
  <div id="app">
    <TopN/>
  </div>
</template>

<script>
import TopN from './components/TopN.vue'

export default {
  name: 'app',
  components: {
    TopN
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
