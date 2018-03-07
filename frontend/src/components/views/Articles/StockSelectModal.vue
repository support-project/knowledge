<template>
  <div class="modal fade" id="StockSelectModal">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span></button>
          <h4 class="modal-title">
            {{$t('StockSelectModal.Title')}}
          </h4>
        </div>

        <div class="modal-body">
          <div v-if="!loaded">
            <i class="fa fa-refresh fa-spin fa-3x fa-fw"></i>
          </div>
          <div v-if="loaded">
            <div class="box box-default">
              <div class="box-header">
                <h3 class="box-title"><!--Liked users--></h3>
                <div class="box-tools">
                  <ul class="pagination pagination-sm no-margin pull-right">
                    <li class="clickable" v-on:click="selectPage(pagination.first)">
                      <a :class="{'disabled': pagination.prev === -1}"><i class="fa fa-step-backward" aria-hidden="true"></i></a>
                    </li>
                    <li class="clickable" v-on:click="selectPage(pagination.prev)">
                      <a :class="{'disabled': pagination.prev === -1}"><i class="fa fa-backward" aria-hidden="true"></i></a>
                    </li>
                    <li class="clickable" v-for="page in pagination.pages" :key="page.label"
                      v-on:click="selectPage(page.offset)">
                      <a :class="{'active': page.current}">{{page.label}}</a>
                    </li>
                    <li class="clickable" v-on:click="selectPage(pagination.next)">
                      <a :class="{'disabled': pagination.next === -1}"><i class="fa fa-forward" aria-hidden="true"></i></a>
                    </li>
                    <li class="clickable" v-on:click="selectPage(pagination.last)">
                      <a :class="{'disabled': pagination.next === -1}"><i class="fa fa-step-forward" aria-hidden="true"></i></a>
                    </li>
                  </ul>
                </div>
              </div>
              <!-- /.box-header -->
              <div class="box-body no-padding">
                <table class="table table-striped">
                  <tbody><tr>
                    <th style="width:70px">{{$t('StockSelectModal.Stocked')}}</th>
                    <th>{{$t('StockSelectModal.StockName')}}</th>
                    <th>{{$t('StockSelectModal.Comment')}}</th>
                  </tr>
                  <tr v-for="(item) in items" :key="item.no">
                    <td class="text-center"><input type="checkbox" v-model="item.stocked"/></td>
                    <td>{{item.stockName}}</td>
                    <td>
                      <input type="text" class="form-control" v-model="item.comment" />
                    </td>
                  </tr>
                </tbody></table>
              </div>
              <!-- /.box-body -->
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" v-on:click="saveStocks()">{{$t('Label.Save')}}</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">{{$t('Label.Close')}}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* global $ */
import { mapState } from 'vuex'

export default {
  name: 'StockSelectModal',
  data () {
    return {
      loaded: false
    }
  },
  computed: {
    ...mapState({
      items: state => state.article.stockSelect.items,
      pagination: state => state.article.stockSelect.pagination
    })
  },
  methods: {
    selectPage (offset) {
      this.loaded = false
      this.$store.dispatch('article/getStocksForSelect', {
        id: this.$route.params.id,
        offset: offset
      }).then(() => {
        this.loaded = true
      })
    },
    saveStocks () {
      this.$store.dispatch('article/saveStocks', {
        id: this.$route.params.id
      }).then(() => {
        $('#StockSelectModal').modal('hide')
      })
    }
  },
  mounted () {
    this.$nextTick(() => {
      $('#StockSelectModal').on('shown.bs.modal', () => {
        this.selectPage(0)
      })
    })
  }
}
</script>

<style>

</style>
