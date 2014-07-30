/*
 * Copyright 2014 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.android.checkout.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.Sku;

import javax.annotation.Nonnull;

class PurchasesAdapter extends ArrayAdapter<Inventory.Purchases> {

	private static final class ViewHolder {

		@Nonnull
		private ImageView icon;

		@Nonnull
		private TextView title;

		@Nonnull
		private TextView count;

		@Nonnull
		private static ViewHolder from(@Nonnull View view) {
			final ViewHolder vh = new ViewHolder();
			vh.icon = (ImageView) view.findViewById(R.id.purchases_icon);
			vh.title = (TextView) view.findViewById(R.id.purchases_title);
			vh.count = (TextView) view.findViewById(R.id.purchases_count);
			return vh;
		}

		public void fill(@Nonnull Inventory.Purchases purchases) {
			final Sku sku = purchases.getSku();
			icon.setImageResource(CheckoutApplication.getSkuIconResId(sku.id));
			title.setText(getTitle(sku));
			count.setText(String.valueOf(purchases.size()));
		}

		@Nonnull
		private String getTitle(Sku sku) {
			final int i = sku.title.lastIndexOf("(");
			if (i > 0) {
				if (sku.title.charAt(i - 1) == ' ') {
					return sku.title.substring(0, i - 1);
				} else {
					return sku.title.substring(0, i);
				}
			}
			return sku.title;
		}
	}

	public PurchasesAdapter(@Nonnull Context context) {
		super(context, 0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final View view;
		final ViewHolder vh;
		if (convertView == null) {
			final LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.purchases, parent, false);
			vh = ViewHolder.from(view);
			view.setTag(R.id.sku_purchases_view_holder, vh);
		} else {
			view = convertView;
			vh = (ViewHolder) view.getTag(R.id.sku_purchases_view_holder);
		}
		vh.fill(getItem(position));
		return view;
	}
}