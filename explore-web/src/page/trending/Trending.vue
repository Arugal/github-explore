<template>
  <el-container direction="vertical">
    <el-header style="height:20%">
      <h1>Trending</h1>
      <p>See what the Github community is most excited about {{timeCodeValue}}.</p>
      <el-row>
        <div id="select-div">
          <el-switch
            v-model="isNew"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="All"
            inactive-text="New"
            style="padding-right:15px;"
          ></el-switch>
          <el-select v-model="timeCodeValue" @change="timeCodeChange()">
            <el-option
              v-for="timeCode in timeCodes"
              :key="timeCode.name"
              :label="timeCode.aliasName"
              :value="timeCode.name"
            ></el-option>
          </el-select>
          <el-select v-model="languageCodeValue" @change="languageCodeChange()">
            <el-option
              v-for="languageCode in languageCodes"
              :key="languageCode.name"
              :label="languageCode.aliasName"
              :value="languageCode.name"
            ></el-option>
          </el-select>
        </div>
      </el-row>
    </el-header>
    <el-main style="height:80%">
      <div id="trending-div" class="explore-content">
        <ul class="trending-ul repo-list">
          <li v-for="trending in trendings" class="trending-li" v-show="isNew || trending.new">
            <div class="d-inline-block col-9 mb-1">
              <h3>
                <a
                  :href="'https://github.com/'+trending.repositorie.owner+'/'+trending.repositorie.name"
                  target="_blank"
                >
                  <span>{{trending.repositorie.owner}} /</span>
                  {{trending.repositorie.name}}
                </a>
              </h3>
            </div>
            <div>
              <p
                class="col-9 d-inline-block text-gray m-0 pr-4"
                v-html="trending.repositorie.describe"
              ></p>
            </div>
            <div class="f6 text-gray mt-2">
              <span class="d-inline-block mr-3">
                <span class="repo-language-color ml-0" v-bind:style="languageSpan"></span>
                <span itemprop="programmingLanguage">{{languageCodeAliasName}}</span>
              </span>
              
              <a
                class="muted-link d-inline-block mr-3"
                :href="'https://github.com/'+trending.repositorie.owner+'/'+trending.repositorie.name+'/stargazers'"
                target="_blank"
              >
                <svg
                  aria-label="star"
                  class="octicon octicon-star"
                  viewBox="0 0 14 16"
                  version="1.1"
                  width="14"
                  height="16"
                  role="img"
                >
                  <path
                    fill-rule="evenodd"
                    d="M14 6l-4.9-.64L7 1 4.9 5.36 0 6l3.6 3.26L2.67 14 7 11.67 11.33 14l-.93-4.74L14 6z"
                  ></path>
                </svg>
                {{trending.star}}
              </a>
              
              <a
                class="muted-link d-inline-block mr-3"
                :href="'https://github.com/'+trending.repositorie.owner+'/'+trending.repositorie.name+'/network'"
                target="_blank"
              >
                <svg
                  aria-label="fork"
                  class="octicon octicon-repo-forked"
                  viewBox="0 0 10 16"
                  version="1.1"
                  width="10"
                  height="16"
                  role="img"
                >
                  <path
                    fill-rule="evenodd"
                    d="M8 1a1.993 1.993 0 0 0-1 3.72V6L5 8 3 6V4.72A1.993 1.993 0 0 0 2 1a1.993 1.993 0 0 0-1 3.72V6.5l3 3v1.78A1.993 1.993 0 0 0 5 15a1.993 1.993 0 0 0 1-3.72V9.5l3-3V4.72A1.993 1.993 0 0 0 8 1zM2 4.2C1.34 4.2.8 3.65.8 3c0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2zm3 10c-.66 0-1.2-.55-1.2-1.2 0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2zm3-10c-.66 0-1.2-.55-1.2-1.2 0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2z"
                  ></path>
                </svg>
                {{trending.fork}}
              </a>
              <span class="d-inline-block float-sm-right mr-3">
                <svg
                  class="octicon octicon-star"
                  viewBox="0 0 14 16"
                  version="1.1"
                  width="14"
                  height="16"
                  aria-hidden="true"
                >
                  <path
                    fill-rule="evenodd"
                    d="M14 6l-4.9-.64L7 1 4.9 5.36 0 6l3.6 3.26L2.67 14 7 11.67 11.33 14l-.93-4.74L14 6z"
                  ></path>
                </svg>
                {{trending.newStar}} stars {{timeCodeAliasName}}
              </span>
              <span
                class="d-inline-block float-sm-right mr-3"
              >{{trending.consecutiveDays}} consecutive {{timeCodeValue}}</span>
              <span class="d-inline-block float-sm-right">
                <span v-if="trending.lastRank == ''">
                  <img src="../../../static/plus.png" class="status-img">
                </span>
                <span v-else-if="trending.lastRank > trending.rank">
                  <img src="../../../static/fall.png" class="status-img">
                  {{trending.lastRank - trending.rank}}
                </span>
                <span v-else-if="trending.lastRank < trending.rank">
                  <img src="../../../static/rise.png" class="status-img">
                  {{trending.rank - trending.lastRank}}
                </span>
                <span v-else>
                  <img src="../../../static/line.png" class="status-img">
                </span>
              </span>
            </div>
          </li>
        </ul>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import { get } from "../../common/js/axiosi.js";
import { getCookie } from "../../common/js/util.js";
import { setCookie } from "../../common/js/util.js";

var cookieTimeCodeName = 'cookie_time_code';
var cookieLanguageCodeName = 'cookie_language_code';

export default {
  name: "Trending",
  data() {
    return {
      timeCodeValue: "weekly",
      timeCodes: [],
      timeCodeAliasName: "this week",
      timeCodeMap: {},
      languageCodes: [],
      languageCodeValue: "java",
      languageCodeMap: {},
      languageCodeAliasName: "Java",
      languageSpan: {
        backgroundColor: "#b07219"
      },
      trendings: [],
      isNew: true
    };
  },
  methods: {
    timeCode: function() {
      get("constant/timeCode").then(response => {
        this.timeCodes = response.data.timeCode;
        this.timeCodes.forEach(element => {
          this.timeCodeMap[element.name] = element.aliasName;
        });
        var cookieTimeCode = getCookie(cookieTimeCodeName)
        if(cookieTimeCode != undefined){
            this.timeCodeValue = cookieTimeCode;
        }
      });
    },
    languageCode: function() {
      get("/constant/languageCode").then(response => {
        this.languageCodes = response.data.languageCode;
        this.languageCodes.forEach(element => {
          this.languageCodeMap[element.name] = {
            color: element.color,
            aliasName: element.aliasName
          };
        });
        var cookieLanguageCode = getCookie(cookieLanguageCodeName)
        if(cookieLanguageCode != undefined){
            this.languageCodeValue = cookieLanguageCode
        }
      });
    },
    trending: function() {
      get("/trending/" + this.languageCodeValue, {
        since: this.timeCodeValue
      }).then(response => {
        this.trendings = response.data.trending;
      });
    },
    codeChange: function() {
      this.trending();
    },
    languageCodeChange: function() {
      this.languageSpan.backgroundColor = this.languageCodeMap[
        this.languageCodeValue
      ].color;
      this.languageCodeAliasName = this.languageCodeMap[
        this.languageCodeValue
      ].aliasName;
      setCookie(cookieLanguageCodeName, this.languageCodeValue);
      this.codeChange();
    },
    timeCodeChange: function() {
      this.timeCodeAliasName = this.timeCodeMap[this.timeCodeValue];
      setCookie(cookieTimeCodeName, this.timeCodeValue)
      this.codeChange();
    }
  },
  mounted() {
    this.languageCode();
    this.timeCode();
    this.trending();
  }
};
</script>

<style>
.el-container {
  height: 100%;
  width: 100%;
  padding: 0px;
}

.el-header {
  color: #333;
  text-align: center;
  line-height: 60px;
  padding: 0px;
}

.el-main {
  color: #333;
  text-align: center;
  line-height: 20px;
  padding: 0px;
}

.el-select {
  line-height: 20px;
}
#select-div {
  text-align: right;
  padding-right: 100px;
}

#trending-div {
  padding-top: 60px;
}

.trending-ul {
  list-style-type: none;
  width: 700px;
  display: inline-block;
  text-align: left;
}

.trending-li {
  padding: 10px;
  overflow: auto;
  border-bottom: 1px solid #e1e4e8 !important;
}

.repo-language-color {
  border-radius: 50%;
  display: inline-block;
  height: 12px;
  position: relative;
  top: 1px;
  width: 12px;
}
.col-9 {
  width: 70%;
}
.col-12 {
  width: 12%;
}
.ml-0 {
  margin-left: 0 !important;
}

.mb-1 {
  margin-bottom: 4px !important;
}

.vertical-align {
  vertical-align: text-bottom;
  display: inline-block;
  fill: currentColor;
  vertical-align: text-top;
}
.muted-link {
  color: #586069 !important;
}
.d-inline-block {
  display: inline-block !important;
}

.d-block {
  display: block !important;
}

.width-full {
  width: 100% !important;
}

.py-4 {
  padding-bottom: 24px !important;
  padding-top: 24px !important;
}

.mr-3 {
  margin-right: 16px !important;
}
a {
  background-color: transparent;
  text-decoration: none;
}

.text-gray {
  color: #586069 !important;
}
.pr-4 {
  padding-right: 24px !important;
}
.py-1 {
  padding-bottom: 4px !important;
  padding-top: 4px !important;
}

.repo-list-item {
  position: relative;
}

.explore-content {
  margin-top: -15px;
}
* {
  box-sizing: border-box;
}
.status-img {
  height: 15px;
  width: 15px;
}
</style>
