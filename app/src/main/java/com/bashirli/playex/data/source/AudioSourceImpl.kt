package com.bashirli.playex.data.source

import com.bashirli.playex.common.Resource

class AudioSourceImpl constructor(
) : AudioSource {

    private fun <T> handleResult(response: () -> T): Resource<T> {
        return try {
            val data = response.invoke()
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}