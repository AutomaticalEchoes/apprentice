# 学徒(Apprentice)
[![Build Status](https://img.shields.io/badge/MinecraftForge-1.20.x-brightgreen)](https://github.com/MinecraftForge/MinecraftForge?branch=1.20.x)

_阅前提示：本人喜欢用<sub title="如果影响你观看就先给你道个歉啦！>-<" >**`注`**</sub>来添加注释。_
## 介绍

本模组添加了几个交易类型，优化交易体验。  
同时，添加了一个特殊交易物品[**工作记录**]，该物品会记录一个交易选项 ，对村民使用会使该村民习得（添加）该物品中记录的交易项。  

## 添加物品
### 工作记录 WorkRecord
- 只能通过与村民交易获得，村民在交易等级升级至大师级时必定获得该交易。
- 随机记录的交易项只会在该物品原产出村民习得的交易项中产生，且支持的交易类型目前仅有原版交易和本模组的修复类交易。
- 学习交易项有职业限制，与该原产出村民一致<sub title="我一个文弱的图书管理员，怎么会打铁呢？" >**`注`**</sub>。
- 使用对象不能为原产出村民<sub title="禁止左脚踩右脚" >**`注`**</sub>。

## 新增交易

|交易类型|效果|村民职业|交易习得|
|---|---|:---:|:---:|
|修复|修复盔甲、武器或工具的耐久。修复量与交易经验奖励随职业等级提升|盔甲匠、工具匠、武器匠|交易等级为学徒（1），必定刷新|
|著作:面板提升|按一定比例提升盔甲、武器或工具已有的面板<sub title="有攻加攻速，有攻击力加攻击力，有XX加XX" >**`注`**</sub>。<br>每个村民提升倍率随机，村民被命名时倍率翻倍。<br>著作类型交易，每个物品仅支持一次。|盔甲匠、工具匠、武器匠|交易等级由专家（4）升级为大师（5）时，习得（已有）交易超过20项|
|著作:附魔|在物品所有可以添加的附魔项<sub title="兼容其他模组附魔，诅咒类型除外" >**`注`**</sub>中选择一项添加物品至且附魔等级为最高级。<br>每个村民附魔项随机，但同个村民同种类型的物品会保持一致<sub title="随机值在交易选项生成时已经固定，例如，根据计算，无论材质所有斧头都会附加一个锋利V，但镐子类附加的可能是耐久III" >**`注`**</sub>，村民被命名时额外再附加一项。<br>著作类型交易，每个物品仅支持一次。|图书馆管理员|交易等级由专家（4）升级为大师（5）时，习得（已有）交易超过20项|
|原版交易|随机生成[工作记录](工作记录)|所有职业|交易等级为大师（5）|
___
**非专业moder,望大家多多海涵.  
如果你发现了什么问题或者有什么建议,可以发邮件给我.~~回不回复随缘~~  
email:AutomaticalEchoes@outlook.com.**
