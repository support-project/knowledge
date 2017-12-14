<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"
    errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
        aria-hidden="true">&times;</button>
    <h4 class="modal-title">Emoji - People</h4>
</div>

<div class="modal-body">
    <div class="te">
    
        <div class="row">
            <ul class="people emojis" id="emoji-people">
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>
                        <img id="e_1" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/bowtie.png" />
                        :<span class="name">bowtie</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>
                        <img id="e_2" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/smile.png" />
                        :<span class="name" data-alternative-name="happy">smile</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>
                        <img id="e_3" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/laughing.png" />
                        :<span class="name">laughing</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>
                        <img id="e_4" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/blush.png" />
                        :<span class="name">blush</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_5" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/smiley.png" />
                        :<span class="name">smiley</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_6" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/relaxed.png" />
                        :<span class="name">relaxed</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_7" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/smirk.png" />
                        :<span class="name">smirk</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_8" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/heart_eyes.png" />
                        :<span class="name">heart_eyes</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_9" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/kissing_heart.png" />
                        :<span class="name">kissing_heart</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_10" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/kissing_closed_eyes.png" />
                        :<span class="name">kissing_closed_eyes</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_11" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/flushed.png" />
                        :<span class="name">flushed</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_12" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/relieved.png" />
                        :<span class="name">relieved</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_13" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/satisfied.png" />
                        :<span class="name">satisfied</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_14" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/grin.png" />
                        :<span class="name">grin</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_15" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/wink.png" />
                        :<span class="name">wink</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_16" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/stuck_out_tongue_winking_eye.png" />
                        :<span class="name">stuck_out_tongue_winking_eye</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_17" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/stuck_out_tongue_closed_eyes.png" />
                        :<span class="name">stuck_out_tongue_closed_eyes</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_18" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/grinning.png" />
                        :<span class="name">grinning</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_19" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/kissing.png" />
                        :<span class="name">kissing</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_20" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/kissing_smiling_eyes.png" />
                        :<span class="name">kissing_smiling_eyes</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_21" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/stuck_out_tongue.png" />
                        :<span class="name">stuck_out_tongue</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_22" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/sleeping.png" />
                        :<span class="name">sleeping</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_23" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/worried.png" />
                        :<span class="name">worried</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_24" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/frowning.png" />
                        :<span class="name">frowning</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_25" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/anguished.png" />
                        :<span class="name">anguished</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_26" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/open_mouth.png" />
                        :<span class="name">open_mouth</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_27" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/grimacing.png" />
                        :<span class="name">grimacing</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_28" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/confused.png" />
                        :<span class="name">confused</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_29" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/hushed.png" />
                        :<span class="name">hushed</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_30" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/expressionless.png" />
                        :<span class="name">expressionless</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_31" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/unamused.png" />
                        :<span class="name">unamused</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_32" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/sweat_smile.png" />
                        :<span class="name" data-alternative-name="happy, relief">sweat_smile</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_33" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/sweat.png" />
                        :<span class="name">sweat</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_34" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/disappointed_relieved.png" />
                        :<span class="name">disappointed_relieved</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_35" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/weary.png" />
                        :<span class="name">weary</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_36" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/pensive.png" />
                        :<span class="name">pensive</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_37" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/disappointed.png" />
                        :<span class="name">disappointed</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_38" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/confounded.png" />
                        :<span class="name">confounded</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_39" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/fearful.png" />
                        :<span class="name">fearful</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_40" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/cold_sweat.png" />
                        :<span class="name">cold_sweat</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_41" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/persevere.png" />
                        :<span class="name">persevere</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_42" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/cry.png" />
                        :<span class="name" data-alternative-name="sad, unhappy">cry</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_43" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/sob.png" />
                        :<span class="name" data-alternative-name="sad, unhappy">sob</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_44" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/joy.png" />
                        :<span class="name">joy</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_45" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/astonished.png" />
                        :<span class="name">astonished</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_46" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/scream.png" />
                        :<span class="name">scream</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_47" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/neckbeard.png" />
                        :<span class="name">neckbeard</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_48" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/tired_face.png" />
                        :<span class="name">tired_face</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_49" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/angry.png" />
                        :<span class="name">angry</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_50" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/rage.png" />
                        :<span class="name">rage</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_51" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/triumph.png" />
                        :<span class="name">triumph</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_52" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/sleepy.png" />
                        :<span class="name">sleepy</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_53" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/yum.png" />
                        :<span class="name">yum</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_54" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/mask.png" />
                        :<span class="name">mask</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_55" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/sunglasses.png" />
                        :<span class="name">sunglasses</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_56" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/dizzy_face.png" />
                        :<span class="name">dizzy_face</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_57" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/imp.png" />
                        :<span class="name">imp</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_58" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/smiling_imp.png" />
                        :<span class="name">smiling_imp</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_59" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/neutral_face.png" />
                        :<span class="name">neutral_face</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_60" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/no_mouth.png" />
                        :<span class="name">no_mouth</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_61" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/innocent.png" />
                        :<span class="name">innocent</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_62" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/alien.png" />
                        :<span class="name">alien</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_63" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/yellow_heart.png" />
                        :<span class="name">yellow_heart</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_64" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/blue_heart.png" />
                        :<span class="name">blue_heart</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_65" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/purple_heart.png" />
                        :<span class="name">purple_heart</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_66" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/heart.png" />
                        :<span class="name">heart</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_67" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/green_heart.png" />
                        :<span class="name">green_heart</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_68" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/broken_heart.png" />
                        :<span class="name">broken_heart</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_69" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/heartbeat.png" />
                        :<span class="name">heartbeat</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_70" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/heartpulse.png" />
                        :<span class="name">heartpulse</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_71" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/two_hearts.png" />
                        :<span class="name">two_hearts</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_72" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/revolving_hearts.png" />
                        :<span class="name">revolving_hearts</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_73" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/cupid.png" />
                        :<span class="name">cupid</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_74" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/sparkling_heart.png" />
                        :<span class="name">sparkling_heart</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_75" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/sparkles.png" />
                        :<span class="name">sparkles</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_76" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/star.png" />
                        :<span class="name">star</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_77" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/star2.png" />
                        :<span class="name">star2</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_78" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/dizzy.png" />
                        :<span class="name">dizzy</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_79" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/boom.png" />
                        :<span class="name">boom</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_80" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/collision.png" />
                        :<span class="name">collision</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_81" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/anger.png" />
                        :<span class="name">anger</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_82" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/exclamation.png" />
                        :<span class="name">exclamation</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_83" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/question.png" />
                        :<span class="name">question</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_84" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/grey_exclamation.png" />
                        :<span class="name">grey_exclamation</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_85" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/grey_question.png" />
                        :<span class="name">grey_question</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_86" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/zzz.png" />
                        :<span class="name" data-alternative-name="sleep">zzz</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_87" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/dash.png" />
                        :<span class="name">dash</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_88" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/sweat_drops.png" />
                        :<span class="name" data-alternative-name="water">sweat_drops</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_89" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/notes.png" />
                        :<span class="name">notes</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_90" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/musical_note.png" />
                        :<span class="name">musical_note</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_91" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/fire.png" />
                        :<span class="name">fire</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_92" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/hankey.png" />
                        :<span class="name">hankey</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_93" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/poop.png" />
                        :<span class="name">poop</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_94" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/shit.png" />
                        :<span class="name">shit</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_95" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/plus1.png" />
                        :<span class="name">+1</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_96" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/thumbsup.png" />
                        :<span class="name">thumbsup</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_97" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/-1.png" />
                        :<span class="name">-1</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_98" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/thumbsdown.png" />
                        :<span class="name">thumbsdown</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_99" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/ok_hand.png" />
                        :<span class="name">ok_hand</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_100" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/punch.png" />
                        :<span class="name">punch</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_101" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/facepunch.png" />
                        :<span class="name">facepunch</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_102" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/fist.png" />
                        :<span class="name">fist</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_103" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/v.png" />
                        :<span class="name">v</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_104" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/wave.png" />
                        :<span class="name">wave</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_105" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/hand.png" />
                        :<span class="name">hand</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_106" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/raised_hand.png" />
                        :<span class="name">raised_hand</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_107" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/open_hands.png" />
                        :<span class="name">open_hands</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_108" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/point_up.png" />
                        :<span class="name">point_up</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_109" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/point_down.png" />
                        :<span class="name">point_down</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_110" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/point_left.png" />
                        :<span class="name">point_left</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_111" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/point_right.png" />
                        :<span class="name">point_right</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_112" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/raised_hands.png" />
                        :<span class="name">raised_hands</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_113" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/pray.png" />
                        :<span class="name">pray</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_114" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/point_up_2.png" />
                        :<span class="name">point_up_2</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_115" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/clap.png" />
                        :<span class="name">clap</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_116" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/muscle.png" />
                        :<span class="name">muscle</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_117" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/metal.png" />
                        :<span class="name">metal</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_118" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/fu.png" />
                        :<span class="name">fu</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_119" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/runner.png" />
                        :<span class="name">runner</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_120" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/running.png" />
                        :<span class="name">running</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_121" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/couple.png" />
                        :<span class="name">couple</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_122" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/family.png" />
                        :<span class="name">family</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_123" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/two_men_holding_hands.png" />
                        :<span class="name" data-alternative-name="gay">two_men_holding_hands</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_124" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/two_women_holding_hands.png" />
                        :<span class="name" data-alternative-name="gay">two_women_holding_hands</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_125" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/dancer.png" />
                        :<span class="name" data-alternative-name="party">dancer</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_126" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/dancers.png" />
                        :<span class="name" data-alternative-name="party">dancers</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_127" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/ok_woman.png" />
                        :<span class="name">ok_woman</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_128" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/no_good.png" />
                        :<span class="name">no_good</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_129" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/information_desk_person.png" />
                        :<span class="name">information_desk_person</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_130" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/raising_hand.png" />
                        :<span class="name">raising_hand</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_131" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/bride_with_veil.png" />
                        :<span class="name">bride_with_veil</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_132" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/person_with_pouting_face.png" />
                        :<span class="name">person_with_pouting_face</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_133" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/person_frowning.png" />
                        :<span class="name">person_frowning</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_134" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/bow.png" />
                        :<span class="name">bow</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_135" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/couplekiss.png" />
                        :<span class="name">couplekiss</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_136" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/couple_with_heart.png" />
                        :<span class="name">couple_with_heart</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_137" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/massage.png" />
                        :<span class="name">massage</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_138" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/haircut.png" />
                        :<span class="name">haircut</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_139" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/nail_care.png" />
                        :<span class="name">nail_care</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_140" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/boy.png" />
                        :<span class="name">boy</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_141" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/girl.png" />
                        :<span class="name">girl</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_142" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/woman.png" />
                        :<span class="name">woman</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_143" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/man.png" />
                        :<span class="name">man</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_144" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/baby.png" />
                        :<span class="name">baby</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_145" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/older_woman.png" />
                        :<span class="name">older_woman</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_146" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/older_man.png" />
                        :<span class="name">older_man</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_147" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/person_with_blond_hair.png" />
                        :<span class="name">person_with_blond_hair</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_148" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/man_with_gua_pi_mao.png" />
                        :<span class="name">man_with_gua_pi_mao</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_149" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/man_with_turban.png" />
                        :<span class="name">man_with_turban</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_150" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/construction_worker.png" />
                        :<span class="name">construction_worker</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_151" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/cop.png" />
                        :<span class="name">cop</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_152" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/angel.png" />
                        :<span class="name">angel</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_153" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/princess.png" />
                        :<span class="name">princess</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_154" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/smiley_cat.png" />
                        :<span class="name" data-alternative-name="animal, happy">smiley_cat</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_155" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/smile_cat.png" />
                        :<span class="name" data-alternative-name="animal, happy">smile_cat</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_156" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/heart_eyes_cat.png" />
                        :<span class="name" data-alternative-name="animal">heart_eyes_cat</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_157" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/kissing_cat.png" />
                        :<span class="name" data-alternative-name="animal">kissing_cat</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_158" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/smirk_cat.png" />
                        :<span class="name" data-alternative-name="animal">smirk_cat</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_159" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/scream_cat.png" />
                        :<span class="name" data-alternative-name="animal">scream_cat</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_160" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/crying_cat_face.png" />
                        :<span class="name" data-alternative-name="animal">crying_cat_face</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_161" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/joy_cat.png" />
                        :<span class="name" data-alternative-name="animal">joy_cat</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_162" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/pouting_cat.png" />
                        :<span class="name" data-alternative-name="animal">pouting_cat</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_163" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/japanese_ogre.png" />
                        :<span class="name">japanese_ogre</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_164" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/japanese_goblin.png" />
                        :<span class="name">japanese_goblin</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_165" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/see_no_evil.png" />
                        :<span class="name">see_no_evil</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_166" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/hear_no_evil.png" />
                        :<span class="name">hear_no_evil</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_167" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/speak_no_evil.png" />
                        :<span class="name">speak_no_evil</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_168" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/guardsman.png" />
                        :<span class="name">guardsman</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_169" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/skull.png" />
                        :<span class="name">skull</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_170" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/feet.png" />
                        :<span class="name">feet</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_171" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/lips.png" />
                        :<span class="name">lips</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_172" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/kiss.png" />
                        :<span class="name">kiss</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_173" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/droplet.png" />
                        :<span class="name" data-alternative-name="water">droplet</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_174" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/ear.png" />
                        :<span class="name">ear</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_175" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/eyes.png" />
                        :<span class="name">eyes</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_176" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/nose.png" />
                        :<span class="name">nose</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_177" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/tongue.png" />
                        :<span class="name">tongue</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_178" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/love_letter.png" />
                        :<span class="name">love_letter</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_179" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/bust_in_silhouette.png" />
                        :<span class="name">bust_in_silhouette</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_180" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/busts_in_silhouette.png" />
                        :<span class="name">busts_in_silhouette</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_181" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/speech_balloon.png" />
                        :<span class="name">speech_balloon</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_182" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/thought_balloon.png" />
                        :<span class="name">thought_balloon</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_183" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/feelsgood.png" />
                        :<span class="name">feelsgood</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_184" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/finnadie.png" />
                        :<span class="name">finnadie</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_185" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/goberserk.png" />
                        :<span class="name">goberserk</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_186" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/godmode.png" />
                        :<span class="name">godmode</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_187" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/hurtrealbad.png" />
                        :<span class="name">hurtrealbad</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_188" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/rage1.png" />
                        :<span class="name">rage1</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_189" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/rage2.png" />
                        :<span class="name">rage2</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_190" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/rage3.png" />
                        :<span class="name">rage3</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_191" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/rage4.png" />
                        :<span class="name">rage4</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_192" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/suspect.png" />
                        :<span class="name">suspect</span>:
                    </div>
                </li>
                <li class="col-sm-6 col-md-4 col-lg-4">
                    <div>

                        <img id="e_193" class="emoji"
                            src="<%=request.getContextPath()%>/bower/emoji-parser/emoji/trollface.png" />
                        :<span class="name">trollface</span>:
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
</div>

