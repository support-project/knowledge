<template>
  <span>
    <div class="modal fade" id="select-target-dialog">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">×</span></button>
            <h4 class="modal-title">{{$t('TargetSelectDialog.Title')}}</h4>
          </div>

          <div class="modal-body">
            <div class="select-area">
              <span if="selected">
                <span class="select-item" v-for="vgroup in selected.groups" :key="vgroup.id">
                  <i class="fa fa-users text-light-blue" aria-hidden="true"></i>&nbsp;{{vgroup.name}}
                  <button type="button" class="btn btn-box-tool" v-on:click="removeTarget(vgroup)">
                    <i class="fa fa-minus-circle"></i>
                  </button>
                </span>
              </span>
              <span if="selected">
                <span class="select-item" v-for="vuser in selected.users" :key="vuser.id">
                  <i class="fa fa-user text-olive" aria-hidden="true"></i>&nbsp;{{vuser.name}}
                  <button type="button" class="btn btn-box-tool" v-on:click="removeTarget(vuser)">
                    <i class="fa fa-minus-circle"></i>
                  </button>
                </span>
              </span>
            </div>

            <div class="row">
            <div class="col-xs-12">
              <div class="box">
                <div class="box-header">
                  <h3 class="box-title">
                    <!--
                    <i class="fa fa-users" aria-hidden="true"></i>
                    <i class="fa fa-user" aria-hidden="true"></i>
                    -->
                    <i class="fa fa-refresh fa-spin fa-1x fa-fw" v-if="loading"></i>
                  </h3>
                  <div class="box-tools">
                    <div class="input-group input-group-sm" style="width: 200px;">
                      <input type="text" name="table_search" class="form-control pull-right" placeholder="Search" v-model="params.keyword">
                      <div class="input-group-btn">
                        <button type="submit" class="btn btn-default" v-on:click="searchTargets()">
                          <i class="fa fa-search"></i>
                        </button>
                        <button type="button" class="btn btn-default input-group-sm" v-on:click="prevSearchTargets()"
                          v-bind:class="{'disabled': params.offset === 0}">
                          <i class="fa fa-arrow-circle-left"></i>
                        </button>
                        <button type="button" class="btn btn-default" v-on:click="nextSearchTargets()">
                          <i class="fa fa-arrow-circle-right"></i>
                       </button>
                      </div>
                    </div>
                  </div>
                </div>
               <div class="box-body table-responsive no-padding" v-if="targets.length > 0">
                  <table class="table table-hover">
                    <tbody><tr>
                      <th style="width:50px;">
                        <i class="fa fa-users text-light-blue" aria-hidden="true"></i>
                        <i class="fa fa-user text-olive" aria-hidden="true"></i>
                      </th>
                      <th>Name</th>
                    </tr>
                    <tr v-for="target in targets" :key="target.type + '-' + target.id"
                      v-bind:class="{'clickable': !target.selected, 'clickdisable': target.selected}"
                      v-on:click="selectTarget(target)">
                      <td class="text-center">
                        <i class="fa fa-users text-light-blue" aria-hidden="true" v-if="target.type === 'group'"></i>
                        <i class="fa fa-user text-olive" aria-hidden="true" v-if="target.type === 'user'"></i>
                      </td>
                      <td>{{target.name}}</td>
                    </tr>
                 </tbody></table>
               </div>
              </div>
            </div>
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
import logger from 'logger'
const LABEL = 'TargetSelectDialog.vue'

export default {
  name: 'TargetSelectDialog',
  computed: {
    ...mapState({
      targets: state => state.targets.targets,
      selected: state => state.targets.selected,
      params: state => state.targets.params,
      loading: state => state.targets.loading
    })
  },
  data () {
    return {
    }
  },
  methods: {
    selectTarget: function (target) {
      this.$store.dispatch('targets/selectTarget', target)
    },
    searchTargets: function () {
      logger.debug(LABEL, 'search targets')
      this.$store.dispatch('targets/searchTargets', this.params)
    },
    removeTarget: function (target) {
      this.$store.dispatch('targets/removeTarget', target)
    },
    nextSearchTargets: function () {
      this.params.offset = this.params.offset + this.params.limit
      this.$store.dispatch('targets/searchTargets', this.params)
    },
    prevSearchTargets: function () {
      this.params.offset = this.params.offset - this.params.limit
      if (this.params.offset < 0) {
        this.params.offset = 0
      }
      this.$store.dispatch('targets/searchTargets', this.params)
    }
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>
.select-area {
  margin-bottom: 5px;
}
.select-item {
  border: 1px solid gray;
  padding: 5px;
  border-radius: 5px;
  display: inline-block;
}
</style>
