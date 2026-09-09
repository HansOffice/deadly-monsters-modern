# Deadly Monsters (Fabric 26.2)

经典恐怖怪物模组 Deadly Monsters 的 Fabric 26.2 移植版。

移植自 [bigbang87/deadly-monsters](https://github.com/bigbang87/deadly-monsters) 与 [ACGaming 的 1.12.2 维护版](https://github.com/ACGaming/deadly-monsters)。保留了原版的全部 12 种怪物、防御工事与特色道具。

## 运行环境

- Minecraft **26.2**
- Fabric Loader **0.19.5+**
- Fabric API **0.159.0+26.2**
- Java **25**

前往 [Releases](https://github.com/HansOffice/deadly-monsters-modern/releases) 下载 `dmonsters-fabric-1.0-26.2.jar`。

## 内容介绍

### 生物
- **突变史蒂夫 (Mutant Steve)**：白天会自燃的高速近战怪物，攻击会破坏周围方块
- **霜冻异怪 (Freezer)**：生成于极寒群系，攻击附带减速，行走时会冻结脚下的水面
- **攀爬者 (Climber)**：能攀爬垂直墙壁，免疫蜘蛛网减速与中毒效果
- **粘液飞头 (Entrail)**：在空中缓慢飘浮，受到非火焰伤害时会分裂出史莱姆
- **腹中胎儿 (Unborn Baby)**：身形小巧且移速较快，靠近时会让玩家间歇失明
- **堕落领袖 (Fallen Leader)**：近战吸血，掉落击退极强的堕落领袖脊柱
- **血腥少女 (Bloody Maiden)**：平时沉睡伪装成普通装饰，惊醒后攻击力极高
- **僵尸鸡 (Zombie Chicken)**：敌对小鸡，会主动攻击玩家并感染普通鸡
- **礼盒怪 (Present)**：伪装成礼物盒，受到攻击后把玩家关进牢笼并刷新苦力怕
- **陌生人 (Stranger)**：潜伏在暗处避开直视，靠近时伴随惊悚音效
- **闹鬼牛 (Haunted Cow)**：必须用剑或弓击杀，使用其他武器攻击会强制将时间切到夜晚
- **异形水鬼 (Topielec)**：水下高速追踪，会将玩家强行往深水里拖拽

### 防御工事与物品
- **强化建筑**：手持强化钢筋右键石头或圆石可转化为抗爆方块，潜行右键可取回钢筋
- **铁丝网与围栏**：生物穿过时会受到持续伤害并大幅减速
- **灵魂之眼**：放置后会吞噬靠近的生物，有几率吐出绿宝石或铁锭
- **圣诞树与礼物盒**：圣诞树会周期性结出礼物盒，破坏随机获得物资或触发危险
- **四阶鱼叉**：分为石、铁、钻石、黑曜石四档，水下击中可捕鱼，对水鬼有克制增伤
- **特殊战利品**：幸运蛋（随机触发效果）、血腥少女之心（生成水或岩浆源）、胎儿之眼（完整采集方块）、日光降临（直接将黑夜切为白天）等

## 配置

配置文件位于 `config/dmonsters.json`：
- 可统一调整全局生命、伤害与移速倍率
- 每种怪物均可单独调整数值倍率、生成权重，或直接关闭生成
- 支持开关突变史蒂夫破坏方块、闹鬼牛强制转夜、水鬼限制鱼叉伤害等机制

## 构建

```bash
gradle build
```

构建产物输出在 `build/libs/` 目录下。

## 鸣谢与协议

- 原模组：[bigbang87/deadly-monsters](https://github.com/bigbang87/deadly-monsters)
- 1.12.2 修复版：[ACGaming/deadly-monsters](https://github.com/ACGaming/deadly-monsters)

本项目采用 [MIT](LICENSE) 协议开源。
