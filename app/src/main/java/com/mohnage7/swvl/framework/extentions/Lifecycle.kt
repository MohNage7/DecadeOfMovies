package com.mohnage7.swvl.framework.extentions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/*
 * Copyright (c) 2020 Dexcom, Inc.
 * Licenses to third-party material that may be incorporated into this software are listed at www.dexcom.com/notices
 *
 */
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(
    liveData: L,
    body: (T) -> Unit
) =
    liveData.observe(this, Observer(body))
