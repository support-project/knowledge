<template>
  <div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: '_PartsTemplate',
  data () {
    return {
    }
  },
  computed: {
    ...mapState({
      pagestate: state => state.pagestate
    })
  },
  methods: {
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>

</style>
