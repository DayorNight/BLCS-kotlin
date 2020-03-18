package com.blcs.common.Base

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants

/**
 * @Author BLCS
 * @Time 2020/3/18 11:24
 */
abstract class BaseApplication(applicationLikePath: String) : TinkerApplication(
    ShareConstants.TINKER_ENABLE_ALL,
    applicationLikePath,
    "com.tencent.tinker.loader.TinkerLoader",
    false
)
