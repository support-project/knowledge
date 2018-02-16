<template>
  <span>
    <div class="modal fade" id="show-liked-users-dialog">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">×</span></button>
            <h4 class="modal-title">
              {{$t('LikesModal.Title')}}
            </h4>
          </div>

          <div class="modal-body">
            <div class="box box-default">
              <div class="box-header">
                <h3 class="box-title"><!--Liked users--></h3>
                <div class="box-tools">
                  <ul class="pagination pagination-sm no-margin pull-right">
                    <li class="clickable" v-on:click="selectPage(likes.pagination.prev)">
                      <a :class="{'disabled': likes.pagination.prev === -1}">«</a>
                    </li>
                    <li class="clickable" v-for="page in likes.pagination.pages" :key="page.label"
                      v-on:click="selectPage(page.offset)">
                      <a :class="{'active': page.current}">{{page.label}}</a>
                    </li>
                    <li class="clickable" v-on:click="selectPage(likes.pagination.next)">
                      <a :class="{'disabled': likes.pagination.next === -1}">»</a>
                    </li>
                  </ul>
                </div>
              </div>
              <!-- /.box-header -->
              <div class="box-body no-padding">
                <table class="table table-striped">
                  <tbody><tr>
                    <th style="width:20px">#</th>
                    <th>{{$t('LikesModal.Name')}}</th>
                    <th>{{$t('LikesModal.InsertedAt')}}</th>
                  </tr>
                  <tr v-for="(like, index) in likes.items" :key="like.no">
                    <td>{{index + parseInt(likes.pagination.offset) + 1}}</td>
                    <td>{{like.userName}}</td>
                    <td>{{like.insertDatetime | dispDate}}</td>
                  </tr>
                </tbody></table>
              </div>
              <!-- /.box-body -->
            </div>
          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">{{$t('Label.Close')}}</button>
          </div>
        </div>
      </div>
    </div>
  </span>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'TargetSelectDialog',
  computed: {
    ...mapState({
      likes: state => state.likes
    })
  },
  data () {
    return {
    }
  },
  methods: {
    selectPage (offset) {
      this.$store.dispatch('likes/selectPage', offset)
    }
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>
</style>
