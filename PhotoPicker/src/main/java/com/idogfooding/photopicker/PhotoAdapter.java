package com.idogfooding.photopicker;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.iwf.photopicker.R;
import me.iwf.photopicker.utils.AndroidLifecycleUtils;

/**
 * PhotoAdapter
 *
 * @author Charles
 */
public class PhotoAdapter extends BaseQuickAdapter<PhotoEntity, BaseViewHolder> {

    private int maxCount = 9; // 最大支持数量
    private boolean addEnable = true; // 是否允许添加

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public boolean isAddEnable() {
        return addEnable;
    }

    public void setAddEnable(boolean addEnable) {
        this.addEnable = addEnable;
    }

    public PhotoAdapter(ArrayList<PhotoEntity> photoPaths) {
        super(photoPaths);
        //Step.1
        setMultiTypeDelegate(new MultiTypeDelegate<PhotoEntity>() {
            @Override
            protected int getItemType(PhotoEntity entity) {
                return entity.getType();
            }
        });
        //Step.2
        getMultiTypeDelegate().registerItemType(PhotoEntity.TYPE_ADD, R.layout.__picker_item_photo_add)
                .registerItemType(PhotoEntity.TYPE_FILE, R.layout.__picker_item_photo)
                .registerItemType(PhotoEntity.TYPE_URL, R.layout.__picker_item_photo);
    }

    @Override
    protected void convert(BaseViewHolder holder, PhotoEntity data) {
        //Step.3
        switch (holder.getItemViewType()) {
            case PhotoEntity.TYPE_ADD:
                // do something
                break;
            case PhotoEntity.TYPE_FILE:
                holder.setVisible(R.id.v_selected, false);
                Uri uri = Uri.fromFile(new File(data.getPath()));
                boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(mContext);
                if (canLoadImage) {
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                            .error(R.drawable.__picker_ic_broken_image_black_48dp);
                    Glide.with(mContext)
                            .load(uri)
                            .apply(options)
                            .thumbnail(0.1f)
                            .into((ImageView) holder.getView(R.id.iv_photo));
                    break;
                }
            case PhotoEntity.TYPE_URL:
                holder.setVisible(R.id.v_selected, false);
                if (AndroidLifecycleUtils.canLoadImage(mContext)) {
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                            .error(R.drawable.__picker_ic_broken_image_black_48dp);
                    Glide.with(mContext)
                            .load(data.getPath())
                            .apply(options)
                            .thumbnail(0.1f)
                            .into((ImageView) holder.getView(R.id.iv_photo));
                    break;
                }
        }

    }

    @Override
    public void setNewData(@Nullable List<PhotoEntity> data) {
        if (addEnable) {
            if (data == null)
                data = new ArrayList<>();
            if (data.size() < getMaxCount())
                data.add(new PhotoEntity(PhotoEntity.TYPE_ADD));
            super.setNewData(data);
        } else {
            super.setNewData(data);
        }
    }

    @Override
    public void addData(@NonNull Collection<? extends PhotoEntity> newData) {
        super.addData(newData);
    }

    public List<PhotoEntity> getRealPhotoEntities() {
        List<PhotoEntity> list = getData();
        for (PhotoEntity entity : list) {
            if (entity.getType() == PhotoEntity.TYPE_ADD) {
                list.remove(entity);
            }
        }
        return list;
    }

    public ArrayList<String> getRealPhotos() {
        ArrayList<String> list = new ArrayList<>();
        for (PhotoEntity entity : getData()) {
            if (entity.getType() != PhotoEntity.TYPE_ADD) {
                list.add(entity.getPath());
            }
        }
        return list;
    }
}